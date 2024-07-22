package dev.dmie.justdoit.service.impl

import com.google.firebase.crashlytics.FirebaseCrashlytics
import dev.dmie.justdoit.service.LogService
import javax.inject.Inject

class LogServiceImpl @Inject constructor(
    private val crashlytics: FirebaseCrashlytics
) : LogService {
    override fun logThrowable(throwable: Throwable) {
        crashlytics.recordException(throwable)
    }
}
