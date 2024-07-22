package dev.dmie.justdoit.screen.sign_up

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import dev.dmie.justdoit.ui.composable.DefaultTopAppBar
import dev.dmie.justdoit.ui.composable.EmailTextField
import dev.dmie.justdoit.ui.composable.PasswordTextField
import dev.dmie.justdoit.ui.theme.JustDoItTheme
import dev.dmie.justdoit.R.string as AppText

@Composable
fun SignUpScreen(
    openAndPopUp: (String, String) -> Unit,
    popUp: () -> Unit,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val email by viewModel.email
    val password by viewModel.password
    val repeatPassword by viewModel.repeatPassword

    SignUpScreenContent(
        email = email,
        password = password,
        repeatPassword = repeatPassword,
        onEmailChange = viewModel::handleEmailChange,
        onPasswordChange = viewModel::handlePasswordChange,
        onRepeatPasswordChange = viewModel::handleRepeatPasswordChange,
        emailError = viewModel.emailError?.let { stringResource(id = it) },
        passwordError = viewModel.passwordError?.let { stringResource(id = it) },
        repeatPasswordError = viewModel.repeatPasswordError?.let { stringResource(id = it) },
        onSignUpClick = { viewModel.signUp(openAndPopUp) },
        popUp = popUp,
    )
}

@Composable
fun SignUpScreenContent(
    email: String,
    password: String,
    repeatPassword: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRepeatPasswordChange: (String) -> Unit,
    emailError: String?,
    passwordError: String?,
    repeatPasswordError: String?,
    onSignUpClick: () -> Unit,
    popUp: () -> Unit
) {
    Scaffold(
        topBar = {
            DefaultTopAppBar(
                title = stringResource(AppText.sign_up_title),
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
                label = stringResource(AppText.email_label),
                onEmailChange = onEmailChange,
                emailError = emailError,
            )
            PasswordTextField(
                password = password,
                label = stringResource(AppText.password_label),
                onPasswordChange = onPasswordChange,
                passwordError = passwordError,
                imeAction = ImeAction.Next
            )
            PasswordTextField(
                password = repeatPassword,
                label = stringResource(id = AppText.repeat_password_label),
                onPasswordChange = onRepeatPasswordChange,
                passwordError = repeatPasswordError,
            )
            Button(onClick = onSignUpClick) {
                Text(text = stringResource(id = AppText.sign_up_button_label))
            }
        }
    }
}

@Composable
@Preview
fun SignUpScreenPreview() {
    JustDoItTheme {
        SignUpScreenContent(
            email = "email@example.com",
            password = "password",
            repeatPassword = "password",
            onEmailChange = {},
            onPasswordChange = {},
            onRepeatPasswordChange = {},
            emailError = null,
            passwordError = null,
            repeatPasswordError = null,
            onSignUpClick = {},
            popUp = {}
        )
    }
}