package dev.dmie.justdoit.screen.profile

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dmie.justdoit.SIGN_IN_SCREEN
import dev.dmie.justdoit.SIGN_UP_SCREEN
import dev.dmie.justdoit.SPLASH_SCREEN
import dev.dmie.justdoit.screen.JustDoItViewModel
import dev.dmie.justdoit.service.AuthenticationService
import dev.dmie.justdoit.service.LogService
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authenticationService: AuthenticationService,
    logService: LogService
) : JustDoItViewModel(logService) {
    val userIsAnonymous = authenticationService.currentUser.map { it.isAnonymous }

    fun signIn(openScreen: (String) -> Unit) = openScreen(SIGN_IN_SCREEN)

    fun signUp(openScreen: (String) -> Unit) = openScreen(SIGN_UP_SCREEN)

    fun signOut(restartApp: (String) -> Unit) {
        launchCatching {
            authenticationService.signOut()
            restartApp(SPLASH_SCREEN)
        }
    }

    fun deleteAccount(restartApp: (String) -> Unit) {
        launchCatching {
            authenticationService.deleteAccount()
            restartApp(SPLASH_SCREEN)
        }
    }
}