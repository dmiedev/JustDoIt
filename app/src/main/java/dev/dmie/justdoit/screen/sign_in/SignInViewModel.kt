package dev.dmie.justdoit.screen.sign_in

import android.util.Patterns
import androidx.annotation.StringRes
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dmie.justdoit.PROFILE_SCREEN
import dev.dmie.justdoit.SIGN_IN_SCREEN
import dev.dmie.justdoit.screen.JustDoItViewModel
import dev.dmie.justdoit.service.AuthenticationService
import dev.dmie.justdoit.service.LogService
import dev.dmie.justdoit.ui.snackbar.SnackbarManager
import dev.dmie.justdoit.ui.snackbar.SnackbarMessage
import dev.dmie.justdoit.R.string as AppText
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authenticationService: AuthenticationService,
    logService: LogService
) : JustDoItViewModel(logService) {
    val email = mutableStateOf("")
    val password = mutableStateOf("")

    @get:StringRes
    val emailError by derivedStateOf {
        if (email.value.isBlank()) {
            AppText.empty_field_error_text
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.value).matches()) {
            AppText.invalid_email_error_text
        } else null
    }

    @get:StringRes
    val passwordError by derivedStateOf {
        if (password.value.isBlank()) {
            AppText.empty_field_error_text
        } else null
    }

    fun handleEmailChange(newValue: String) {
        email.value = newValue
    }

    fun handlePasswordChange(newValue: String) {
        password.value = newValue
    }

    fun signIn(openAndPopUp: (String, String) -> Unit) {
        if (emailError != null || passwordError != null) {
            return
        }

        launchCatching {
            authenticationService.authenticate(email.value, password.value)
            openAndPopUp(PROFILE_SCREEN, SIGN_IN_SCREEN)
        }
    }

    fun sendRecoveryEmail() {
        if (emailError != null) {
            return
        }

        launchCatching {
            authenticationService.sendRecoveryEmail(email.value)
            SnackbarManager.showMessage(AppText.recovery_email_sent_text)
        }
    }
}