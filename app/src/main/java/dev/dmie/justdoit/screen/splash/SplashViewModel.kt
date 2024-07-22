package dev.dmie.justdoit.screen.splash

import androidx.compose.runtime.mutableStateOf
import com.google.firebase.auth.FirebaseAuthException
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dmie.justdoit.SPLASH_SCREEN
import dev.dmie.justdoit.TASK_LISTS_SCREEN
import dev.dmie.justdoit.screen.JustDoItViewModel
import dev.dmie.justdoit.service.AuthenticationService
import dev.dmie.justdoit.service.LogService
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authenticationService: AuthenticationService,
    logService: LogService
) : JustDoItViewModel(logService) {
    val showError = mutableStateOf(false)

    fun authenticate(openAndPopUp: (String, String) -> Unit) {
        showError.value = false
        if (authenticationService.hasUser) {
            openAndPopUp(TASK_LISTS_SCREEN, SPLASH_SCREEN)
            return
        }
        launchCatching(showErrorSnackbar = false) {
            try {
                authenticationService.createAnonymousAccount()
            } catch (ex: FirebaseAuthException) {
                showError.value = true
                throw ex
            }
            openAndPopUp(TASK_LISTS_SCREEN, SPLASH_SCREEN)
        }
    }
}
