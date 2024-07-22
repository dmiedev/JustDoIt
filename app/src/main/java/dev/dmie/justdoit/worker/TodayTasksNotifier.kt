package dev.dmie.justdoit.worker

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dev.dmie.justdoit.DEEP_LINK_BASE_URL
import dev.dmie.justdoit.JustDoItHiltApp
import dev.dmie.justdoit.R
import dev.dmie.justdoit.TODAY_TASKS_SCREEN
import dev.dmie.justdoit.model.Task
import dev.dmie.justdoit.service.LogService
import dev.dmie.justdoit.service.StorageService
import kotlinx.coroutines.flow.first
import kotlin.random.Random
import dev.dmie.justdoit.R.drawable as AppIcon

@HiltWorker
class TodayTasksNotifier @AssistedInject constructor(
    private val storageService: StorageService,
    private val logService: LogService,
    @Assisted applicationContext: Context,
    @Assisted workerParams: WorkerParameters
): CoroutineWorker(applicationContext, workerParams) {
    override suspend fun doWork(): Result {
        kotlin
            .runCatching {
                val tasks = storageService.getTodayTasks(Task.Sort.Priority).first()
                if (tasks.isNotEmpty()) {
                    sendNotification(tasks)
                }
            }
            .onFailure {
                logService.logThrowable(it)
                return Result.failure()
            }

        return Result.success()
    }

    private fun sendNotification(tasks: List<Task>) {
        val resources = applicationContext.resources
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("$DEEP_LINK_BASE_URL/$TODAY_TASKS_SCREEN"))
        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(applicationContext, JustDoItHiltApp.TODAY_TASKS_CHANNEL_ID)
            .setSmallIcon(AppIcon.ic_check_box)
            .setContentTitle(resources.getQuantityString(R.plurals.tasks_due_title, tasks.size, tasks.size))
            .setContentText(
                tasks.joinToString(
                    limit = 3,
                    truncated = resources.getQuantityString(R.plurals.tasks_due_more_text, tasks.size - 3, tasks.size - 3)
                ) { it.title }
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_REMINDER)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        try {
            NotificationManagerCompat.from(applicationContext).notify(Random.nextInt(), notification)
        } catch (ex: SecurityException) {
            // The app does not have the required permission, so the notification cannot be shown
        }
    }
}
