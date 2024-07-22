package dev.dmie.justdoit.screen.sign_up

import android.util.Patterns
import androidx.annotation.StringRes
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dmie.justdoit.PROFILE_SCREEN
import dev.dmie.justdoit.SIGN_UP_SCREEN
import dev.dmie.justdoit.screen.JustDoItViewModel
import dev.dmie.justdoit.service.AuthenticationService
import dev.dmie.justdoit.service.LogService
import dev.dmie.justdoit.R.string as AppText
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authenticationService: AuthenticationService,
    logService: LogService
) : JustDoItViewModel(logService) {
    val email = mutableStateOf("")
    val password = mutableStateOf("")
    val repeatPassword = mutableStateOf("")

    @get:StringRes
    val emailError by derivedStateOf {
        if (email.value.isBlank()) {
            AppText.empty_field_error_text
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.value.trim()).matches()) {
            AppText.invalid_email_error_text
        } else null
    }

    @get:StringRes
    val passwordError by derivedStateOf {
        if (password.value.isEmpty()) {
            AppText.empty_field_error_text
        } else if (password.value.length < MIN_PASSWORD_LENGTH) {
            AppText.password_too_short_error_text
        } else null
    }

    @get:StringRes
    val repeatPasswordError by derivedStateOf {
        if (password.value != repeatPassword.value) {
            AppText.password_not_identical_error_text
        } else null
    }

    fun handleEmailChange(newValue: String) {
        email.value = newValue
    }

    fun handlePasswordChange(newValue: String) {
        password.value = newValue
    }

    fun handleRepeatPasswordChange(newValue: String) {
        repeatPassword.value = newValue
    }

    fun signUp(openAndPopUp: (String, String) -> Unit) {
        if (emailError != null || passwordError != null || repeatPasswordError != null) {
            return
        }

        launchCatching {
            authenticationService.linkAccount(email.value.trim(), password.value)
            openAndPopUp(PROFILE_SCREEN, SIGN_UP_SCREEN)
        }
    }

    companion object {
        private const val MIN_PASSWORD_LENGTH = 6
    }
}