package dev.dmie.justdoit

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.HiltAndroidApp
import dev.dmie.justdoit.worker.TodayTasksNotifier
import java.util.Calendar
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class JustDoItHiltApp : Application(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder().setWorkerFactory(workerFactory).build()

    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
        scheduleTodayTasksNotifier()
        // testTodayTaskNotification()
    }

    private fun createNotificationChannels() {
        val todayTasksChannel = NotificationChannel(
            TODAY_TASKS_CHANNEL_ID,
            "Today tasks",
            NotificationManager.IMPORTANCE_HIGH
        ).apply { description = "Reminders for tasks due today" }
        NotificationManagerCompat.from(applicationContext).createNotificationChannel(todayTasksChannel)
    }

    private fun scheduleTodayTasksNotifier() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val currentTime = Calendar.getInstance()
        val tomorrowMorningTime = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, 1)
            set(Calendar.HOUR_OF_DAY, 9)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val initialDelay = tomorrowMorningTime.timeInMillis - currentTime.timeInMillis
        val workRequest = PeriodicWorkRequestBuilder<TodayTasksNotifier>(1, TimeUnit.DAYS)
            .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
            .setConstraints(constraints)
            .build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            TODAY_TASK_NOTIFIER_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

    private fun testTodayTaskNotification() {
        WorkManager.getInstance(this).enqueue(
            OneTimeWorkRequestBuilder<TodayTasksNotifier>().build()
        )
    }

    companion object {
        const val TODAY_TASKS_CHANNEL_ID = "today_tasks_channel"
        const val TODAY_TASK_NOTIFIER_WORK_NAME = "today_task_notifier_work"
    }
}
