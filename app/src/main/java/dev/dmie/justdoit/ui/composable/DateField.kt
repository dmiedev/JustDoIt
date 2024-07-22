package dev.dmie.justdoit.ui.composable

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import dev.dmie.justdoit.R.string as AppText
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

val dateFormat = SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.US)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateField(
    date: Date?,
    onDateSelected: (Date?) -> Unit,
    label: String,
) {
    var showDialog by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = if (date != null) dateFormat.format(date) else "",
        label = { Text(label) },
        onValueChange = {},
        supportingText = {},
        readOnly = true,
        // So clicking works
        enabled = false,
        // Hide the disabled colors
        colors = OutlinedTextFieldDefaults.colors(
            disabledBorderColor =  MaterialTheme.colorScheme.outline,
            disabledTextColor = MaterialTheme.colorScheme.onSurface,
            disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledTrailingIconColor = MaterialTheme.colorScheme.outline,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
        ),
        modifier = Modifier.clickable { showDialog = true },
        trailingIcon = {
            if (date != null) {
                IconButton(onClick = { onDateSelected(null) }) {
                    Icon(Icons.Filled.Clear, stringResource(AppText.clear_button_label))
                }
            }
        }
    )

    if (showDialog) {
        val datePickerState = rememberDatePickerState()
        val confirmEnabled = remember {
            derivedStateOf { datePickerState.selectedDateMillis != null }
        }

        DatePickerDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                        onDateSelected(Date(datePickerState.selectedDateMillis!!))
                    },
                    enabled = confirmEnabled.value
                ) { Text(stringResource(AppText.ok_button_label)) }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text(stringResource(AppText.cancel_button_label))
                }
            }
        ) { DatePicker(state = datePickerState) }
    }
}