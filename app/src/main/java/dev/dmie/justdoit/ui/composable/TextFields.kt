package dev.dmie.justdoit.ui.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import dev.dmie.justdoit.R.drawable as AppIcon
import dev.dmie.justdoit.R.string as AppText

@Composable
fun EmailTextField(
    email: String,
    onEmailChange: (String) -> Unit,
    emailError: String? = null,
    label: String,
) {
    OutlinedTextField(
        value = email,
        label = { Text(label) },
        onValueChange = onEmailChange,
        isError = emailError != null,
        supportingText = {
            if (emailError != null) {
                Text(text = emailError)
            }
        },
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        )
    )
}

@Composable
fun PasswordTextField(
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordError: String? = null,
    label: String,
    imeAction: ImeAction = ImeAction.Done
) {
    val passwordVisualTransformation = remember { PasswordVisualTransformation() }
    var showPassword by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = password,
        label = { Text(label) },
        onValueChange = onPasswordChange,
        isError = passwordError != null,
        supportingText = {
            if (passwordError != null) {
                Text(text = passwordError)
            }
        },
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction,
        ),
        visualTransformation = if (showPassword)
            VisualTransformation.None
        else
            passwordVisualTransformation,
        trailingIcon = {
            Icon(
                painter = painterResource(
                    if (showPassword)
                        AppIcon.ic_visibility_off
                    else
                        AppIcon.ic_visibility
                ),
                contentDescription = stringResource(AppText.password_visibility_button_label),
                modifier = Modifier.clickable { showPassword = !showPassword }
            )
        }
    )
}
