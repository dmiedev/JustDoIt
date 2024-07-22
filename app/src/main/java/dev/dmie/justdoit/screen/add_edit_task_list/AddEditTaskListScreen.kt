package dev.dmie.justdoit.screen.add_edit_task_list

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.dmie.justdoit.ui.composable.DefaultTopAppBar
import dev.dmie.justdoit.model.TaskList
import dev.dmie.justdoit.ui.theme.JustDoItTheme
import dev.dmie.justdoit.R.string as AppText

@Composable
fun AddEditTaskListScreen(
    popUp: () -> Unit,
    viewModel: AddEditTaskListViewModel = hiltViewModel()
) {
    val taskList by viewModel.taskList

    AddEditTaskListContent(
        taskList = taskList,
        taskListNameError = viewModel.taskListNameError?.let { stringResource(it) },
        onNameChange = viewModel::handleNameChange,
        onIconChange = viewModel::handleIconChange,
        onDoneClick = { viewModel.finish(popUp) },
        onDeleteClick = { viewModel.delete(popUp) },
        popUp = popUp
    )
}

@Composable
fun AddEditTaskListContent(
    taskList: TaskList,
    taskListNameError: String?,
    onNameChange: (String) -> Unit,
    onIconChange: (TaskList.Icon) -> Unit,
    onDoneClick: () -> Unit,
    onDeleteClick: () -> Unit,
    popUp: () -> Unit
) {
    Scaffold(
        topBar = {
            DefaultTopAppBar(
                title = stringResource(
                    if (taskList.isNew())
                        AppText.add_task_list_title
                    else
                        AppText.edit_task_list_title
                ),
                popUp = popUp,
                actions = {
                    if (!taskList.isNew()) {
                        DeleteButton(onDeleteClick)
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = onDoneClick) {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = stringResource(id = AppText.done_button_label)
                )
                Spacer(Modifier.width(8.dp))
                Text(text = stringResource(id = AppText.done_button_label))
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(
                value = taskList.name,
                label = { Text(stringResource(id = AppText.name_label)) },
                onValueChange = onNameChange,
                isError = taskListNameError != null,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                supportingText = {
                    if (taskListNameError != null) {
                        Text(text = taskListNameError)
                    }
                }
            )
            Spacer(Modifier.height(32.dp))
            Text(text = stringResource(id = AppText.icon_label))
            Spacer(Modifier.height(16.dp))
            Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                TaskList.Icon.entries.forEach { icon ->
                    Spacer(Modifier.width(16.dp))
                    IconButton(onClick = { onIconChange(icon) }) {
                        Icon(
                            painter = painterResource(icon.drawableId),
                            contentDescription = "${AppText.icon_label}: ${icon.name}",
                            modifier = Modifier.size(32.dp),
                            tint = if (icon == taskList.icon)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.secondary,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DeleteButton(onDeleteClick: () -> Unit) {
    var showWarningDialog by remember { mutableStateOf(false) }

    IconButton(onClick = { showWarningDialog = true }) {
        Icon(Icons.Filled.Delete, stringResource(id = AppText.delete_button_label))
    }

    if (showWarningDialog) {
        AlertDialog(
            title = { Text(stringResource(id = AppText.delete_task_list_dialog_title)) },
            text = { Text(stringResource(id = AppText.delete_task_list_dialog_text)) },
            dismissButton = {
                TextButton(onClick = { showWarningDialog = false }) {
                    Text(stringResource(id = AppText.cancel_button_label))
                }
            },
            confirmButton = {
                TextButton(onClick = { onDeleteClick(); showWarningDialog = false }) {
                    Text(stringResource(id = AppText.delete_button_label))
                }
            },
            onDismissRequest = { showWarningDialog = false }
        )
    }
}

@Composable
@Preview
fun AddEditTaskListScreenPreview() {
    JustDoItTheme {
        AddEditTaskListContent(
            taskList = TaskList(
                id = "1",
                name = "Task list",
                icon = TaskList.Icon.Gaming,
                userId = "123"
            ),
            taskListNameError = null,
            onNameChange = {},
            onIconChange = {},
            onDoneClick = {},
            onDeleteClick = {},
            popUp = {}
        )
    }
}