package dev.dmie.justdoit.screen.sign_in

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.dmie.justdoit.ui.composable.DefaultTopAppBar
import dev.dmie.justdoit.ui.composable.EmailTextField
import dev.dmie.justdoit.ui.composable.PasswordTextField
import dev.dmie.justdoit.ui.theme.JustDoItTheme
import dev.dmie.justdoit.R.string as AppText

@Composable
fun SignInScreen(
    openAndPopUp: (String, String) -> Unit,
    popUp: () -> Unit,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val email by viewModel.email
    val password by viewModel.password

    SignInScreenContent(
        email = email,
        password = password,
        onEmailChange = viewModel::handleEmailChange,
        onPasswordChange = viewModel::handlePasswordChange,
        emailError = viewModel.emailError?.let { stringResource(it) },
        passwordError = viewModel.passwordError?.let { stringResource(it) },
        onSignInClick = { viewModel.signIn(openAndPopUp) },
        onSendRecoveryEmailClick = viewModel::sendRecoveryEmail,
        popUp = popUp
    )
}

@Composable
fun SignInScreenContent(
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    emailError: String?,
    passwordError: String?,
    onSignInClick: () -> Unit,
    onSendRecoveryEmailClick: () -> Unit,
    popUp: () -> Unit
) {
    Scaffold(
        topBar = {
            DefaultTopAppBar(
                title = stringResource(AppText.sign_in_title),
                popUp = popUp
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EmailTextField(
                email = email,
                label = stringResource(id = AppText.email_label),
                onEmailChange = onEmailChange,
                emailError = emailError,
            )
            PasswordTextField(
                password = password,
                label = stringResource(id = AppText.password_label),
                onPasswordChange = onPasswordChange,
                passwordError = passwordError,
            )
            Button(onClick = onSignInClick) {
                Text(stringResource(AppText.sign_in_button_label))
            }
            Spacer(modifier = Modifier.height(32.dp))
            Text(stringResource(AppText.forgot_password_label))
            TextButton(onClick = onSendRecoveryEmailClick) {
                Text(stringResource(AppText.send_recovery_email_button_label))
            }
        }
    }
}

@Composable
@Preview
fun SignUpScreenPreview() {
    JustDoItTheme {
        SignInScreenContent(
            email = "email@example.com",
            password = "password",
            onEmailChange = {},
            onPasswordChange = {},
            emailError = null,
            passwordError = null,
            onSignInClick = {},
            onSendRecoveryEmailClick = {},
            popUp = {}
        )
    }
}