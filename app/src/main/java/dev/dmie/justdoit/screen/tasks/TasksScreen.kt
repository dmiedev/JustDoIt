package dev.dmie.justdoit.screen.tasks

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.dmie.justdoit.ui.composable.DefaultTopAppBar
import dev.dmie.justdoit.ui.composable.TaskListItem
import dev.dmie.justdoit.ui.composable.TaskSortButton
import dev.dmie.justdoit.model.Task
import dev.dmie.justdoit.ui.theme.JustDoItTheme
import dev.dmie.justdoit.R.string as AppText
import java.util.Date

@Composable
fun TasksScreen(
    openScreen: (String) -> Unit,
    popUp: () -> Unit,
    viewModel: TasksViewModel = hiltViewModel()
) {
    val taskList by viewModel.taskList
    val tasks by viewModel.tasks.collectAsStateWithLifecycle(emptyList())
    val sort by viewModel.sort.collectAsStateWithLifecycle(Task.Sort.None)

    TasksScreenContent(
        taskListName = taskList.name,
        tasks = tasks,
        sort = sort,
        onAddClick = { viewModel.addTask(openScreen) } ,
        onEditClick = { viewModel.editTask(openScreen, it) },
        onCheckChange = viewModel::checkTask,
        onDeleteClick = viewModel::deleteTask,
        onSortChange = viewModel::setSort,
        popUp = popUp
    )
}

@Composable
fun TasksScreenContent(
    taskListName: String,
    tasks: List<Task>,
    sort: Task.Sort,
    onAddClick: (() -> Unit)? = null,
    onEditClick: (Task) -> Unit,
    onCheckChange: (Task) -> Unit,
    onDeleteClick: (Task) -> Unit,
    onSortChange: (Task.Sort) -> Unit,
    popUp: () -> Unit,
) {
    Scaffold(
        topBar = {
            DefaultTopAppBar(
                title = taskListName,
                popUp = popUp,
                actions = { TaskSortButton(sort, onSortChange) }
            )
        },
        floatingActionButton = {
            if (onAddClick != null) {
                ExtendedFloatingActionButton(onClick = onAddClick) {
                    Icon(Icons.Filled.Add, stringResource(AppText.add_task_button_label))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(stringResource(AppText.add_task_button_label))
                }
            }
        }
    ) { innerPadding ->
        if (tasks.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    stringResource(AppText.no_tasks_text),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .consumeWindowInsets(innerPadding)
                    .fillMaxWidth()
                    .fillMaxHeight(),
                contentPadding = innerPadding
            ) {
                items(tasks) { task ->
                    TaskListItem(
                        task = task,
                        onEditClick = { onEditClick(task) },
                        onCheckChange = { onCheckChange(task) },
                        onDeleteClick = { onDeleteClick(task) },
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun TasksScreenPreview() {
    JustDoItTheme {
        TasksScreenContent(
            taskListName = "Family",
            tasks = listOf(
                Task(
                    title = "Clean the house",
                    description = "Vacuum and mop the floors and dust the furniture and shelves and clean the windows",
                    dueDate = Date(),
                    priority = Task.Priority.High.ordinal,
                    completed = false
                ),
                Task(title = "Buy groceries", completed = true)
            ),
            onAddClick = {},
            onEditClick = {},
            onCheckChange = {},
            onDeleteClick = {},
            popUp = {},
            onSortChange = {},
            sort = Task.Sort.DueDate
        )
    }
}