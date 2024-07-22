package dev.dmie.justdoit.screen.add_edit_task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.dmie.justdoit.ui.composable.DateField
import dev.dmie.justdoit.ui.composable.DefaultTopAppBar
import dev.dmie.justdoit.ui.composable.DropdownField
import dev.dmie.justdoit.model.Task
import dev.dmie.justdoit.ui.theme.JustDoItTheme
import dev.dmie.justdoit.R.string as AppText
import java.util.Date

@Composable
fun AddEditTaskScreen(
    popUp: () -> Unit,
    viewModel: AddEditTaskViewModel = hiltViewModel()
) {
    val task by viewModel.task

    AddEditTaskScreenContent(
        task = task,
        onDoneClick = { viewModel.finish(popUp) },
        onDeleteClick = { viewModel.delete(popUp) },
        onTitleChange = viewModel::handleTitleChange,
        titleError = viewModel.titleError?.let { stringResource(id = it) },
        onDescriptionChange = viewModel::handleDescriptionChange,
        onDateChange = viewModel::handleDateChange,
        onPriorityChange = viewModel::handlePriorityChange,
        popUp = popUp
    )
}

@Composable
fun AddEditTaskScreenContent(
    task: Task,
    onDoneClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onTitleChange: (String) -> Unit,
    titleError: String?,
    onDescriptionChange: (String) -> Unit,
    onDateChange: (Date?) -> Unit,
    onPriorityChange: (Task.Priority) -> Unit,
    popUp: () -> Unit
) {
    Scaffold(
        topBar = {
            DefaultTopAppBar(
                title = stringResource(
                    if (task.isNew())
                        AppText.add_task_title
                    else
                        AppText.edit_task_title
                ),
                popUp = popUp,
                actions = {
                    if (!task.isNew()) {
                        IconButton(onClick = onDeleteClick) {
                            Icon(Icons.Filled.Delete, stringResource(AppText.delete_button_label))
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = onDoneClick) {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = stringResource(AppText.done_button_label)
                )
                Spacer(Modifier.width(8.dp))
                Text(stringResource(AppText.done_button_label))
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
            OutlinedTextField(
                value = task.title,
                label = { Text(stringResource(id = AppText.title_label)) },
                onValueChange = onTitleChange,
                isError = titleError != null,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                supportingText = {
                    if (titleError != null) {
                        Text(text = titleError)
                    }
                }
            )
            OutlinedTextField(
                value = task.description,
                label = { Text(stringResource(id = AppText.description_label)) },
                onValueChange = onDescriptionChange,
                supportingText = {},
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            )
            DateField(
                date = task.dueDate,
                onDateSelected = onDateChange,
                label = stringResource(id = AppText.due_date_label),
            )
            DropdownField(
                options = Task.Priority.entries,
                selectedOption = Task.Priority.entries[task.priority],
                onOptionChange = onPriorityChange,
                label = stringResource(id = AppText.priority_label),
            )
        }
    }
}

@Composable
@Preview
fun AddEditTaskScreenPreview() {
    JustDoItTheme {
        AddEditTaskScreenContent(
            task = Task(
                id = "1",
                title = "Buy milk",
                description = "Buy milk from the store, don't forget to check the expiration date",
                dueDate = null,
                priority = Task.Priority.Medium.ordinal,
                completed = false
            ),
            onDoneClick = {},
            onDeleteClick = {},
            onTitleChange = {},
            titleError = null,
            onDescriptionChange = {},
            onDateChange = {},
            onPriorityChange = {},
            popUp = {}
        )
    }
}