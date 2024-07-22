package dev.dmie.justdoit.screen.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.dmie.justdoit.ui.composable.DefaultTopAppBar
import dev.dmie.justdoit.ui.theme.JustDoItTheme
import dev.dmie.justdoit.R.drawable as AppIcon
import dev.dmie.justdoit.R.string as AppText

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    openScreen: (String) -> Unit,
    restartApp: (String) -> Unit,
    popUp: () -> Unit,
) {
    val userIsAnonymous by viewModel.userIsAnonymous.collectAsStateWithLifecycle(false)

    ProfileScreenContent(
        userIsAnonymous = userIsAnonymous,
        onSignUpClick = { viewModel.signUp(openScreen) },
        onSignInClick = { viewModel.signIn(openScreen) },
        onSignOutClick = { viewModel.signOut(restartApp) },
        onDeleteAccountClick = { viewModel.deleteAccount(restartApp) },
        popUp = popUp
    )
}

@Composable
fun ProfileScreenContent(
    userIsAnonymous: Boolean,
    onSignUpClick: () -> Unit,
    onSignInClick: () -> Unit,
    onSignOutClick: () -> Unit,
    onDeleteAccountClick: () -> Unit,
    popUp: () -> Unit
) {
    Scaffold(
        topBar = {
            DefaultTopAppBar(
                title = stringResource(id = AppText.profile_title),
                popUp = popUp
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())) {
            if (userIsAnonymous) {
                ListItem(
                    leadingContent = {
                        Icon(
                            painterResource(AppIcon.ic_person_add),
                            stringResource(id = AppText.sign_up_button_label)
                        )
                    },
                    headlineContent = {
                        Text(stringResource(id = AppText.sign_up_button_label))
                    },
                    modifier = Modifier.clickable(onClick = onSignUpClick)
                )
                ListItem(
                    leadingContent = {
                        Icon(
                            painterResource(AppIcon.ic_login),
                            stringResource(id = AppText.sign_in_button_label)
                        )
                    },
                    headlineContent = {
                        Text(stringResource(id = AppText.sign_in_button_label))
                    },
                    modifier = Modifier.clickable(onClick = onSignInClick)
                )
            } else {
                SignOutTile(onSignOutClick)
                DeleteAccountTile(onDeleteAccountClick)
            }
        }
    }
}

@Composable
private fun SignOutTile(onSignOutClick: () -> Unit) {
    var showWarningDialog by remember { mutableStateOf(false) }

    ListItem(
        leadingContent = {
            Icon(
                painterResource(AppIcon.ic_exit_to_app),
                stringResource(id = AppText.sign_out_button_label)
            )
        },
        headlineContent = { Text(stringResource(id = AppText.sign_out_button_label)) },
        modifier = Modifier.clickable { showWarningDialog = true }
    )

    if (showWarningDialog) {
        AlertDialog(
            title = { Text(stringResource(id = AppText.sign_out_dialog_title)) },
            text = { Text(stringResource(id = AppText.sign_out_dialog_text)) },
            dismissButton = {
                TextButton(onClick = { showWarningDialog = false }) {
                    Text(stringResource(id = AppText.cancel_button_label))
                }
            },
            confirmButton = {
                TextButton(onClick = { onSignOutClick(); showWarningDialog = false }) {
                    Text(stringResource(id = AppText.sign_out_button_label))
                }
            },
            onDismissRequest = { showWarningDialog = false }
        )
    }
}

@Composable
private fun DeleteAccountTile(onDeleteAccountClick: () -> Unit) {
    var showWarningDialog by remember { mutableStateOf(false) }

    ListItem(
        leadingContent = {
            Icon(
                painterResource(AppIcon.ic_person_off),
                stringResource(id = AppText.delete_account_button_label)
            )
        },
        headlineContent = { Text(stringResource(id = AppText.delete_account_button_label)) },
        modifier = Modifier.clickable { showWarningDialog = true }
    )

    if (showWarningDialog) {
        AlertDialog(
            title = { Text(stringResource(id = AppText.delete_account_dialog_title)) },
            text = { Text(stringResource(id = AppText.delete_account_dialog_text)) },
            dismissButton = {
                TextButton(onClick = { showWarningDialog = false }) {
                    Text(stringResource(id = AppText.cancel_button_label))
                }
            },
            confirmButton = {
                TextButton(onClick = { onDeleteAccountClick(); showWarningDialog = false }) {
                    Text(stringResource(id = AppText.delete_button_label))
                }
            },
            onDismissRequest = { showWarningDialog = false }
        )
    }
}

@Composable
@Preview
fun ProfileScreenPreview() {
    JustDoItTheme {
        ProfileScreenContent(
            userIsAnonymous = false,
            onSignUpClick = {},
            onSignInClick = {},
            onSignOutClick = {},
            onDeleteAccountClick = {},
            popUp = {}
        )
    }
}