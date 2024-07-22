package dev.dmie.justdoit.screen.task_lists

import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.dmie.justdoit.ui.composable.DefaultTopAppBar
import dev.dmie.justdoit.model.TaskList
import dev.dmie.justdoit.ui.theme.JustDoItTheme
import dev.dmie.justdoit.R.drawable as AppIcon
import dev.dmie.justdoit.R.string as AppText

@Composable
fun TaskListsScreen(
    openScreen: (String) -> Unit,
    viewModel: TaskListsViewModel = hiltViewModel()
) {
    val taskLists by viewModel.taskLists.collectAsStateWithLifecycle(emptyList())

    TaskListsScreenContent(
        taskLists = taskLists,
        onAddClick = { viewModel.addTaskList(openScreen) },
        onEditClick = { viewModel.editTaskList(openScreen, it) },
        onViewClick = { viewModel.viewTaskList(openScreen, it) },
        onTodayViewClick = { viewModel.viewTodayTaskList(openScreen) },
        onProfileClick = { viewModel.openProfile(openScreen) },
        onSearchClick = { viewModel.openSearch(openScreen) }
    )
}

@Composable
fun TaskListsScreenContent(
    taskLists: List<TaskList>,
    onAddClick: () -> Unit,
    onEditClick: (TaskList) -> Unit,
    onViewClick: (TaskList) -> Unit,
    onTodayViewClick: () -> Unit,
    onProfileClick: () -> Unit,
    onSearchClick: () -> Unit,
) {
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = onAddClick) {
                Icon(Icons.Filled.Add, stringResource(AppText.add_list_button_label))
                Spacer(modifier = Modifier.width(8.dp))
                Text(stringResource(AppText.add_list_button_label))
            }
        },
        topBar = {
            DefaultTopAppBar(
                title = stringResource(AppText.task_lists_title),
                actions = {
                    IconButton(onClick = onProfileClick) {
                        Icon(Icons.Filled.Person, stringResource(AppText.profile_title))
                    }
                    IconButton(onClick = onSearchClick) {
                        Icon(Icons.Filled.Search, stringResource(AppText.search_title))
                    }
                }
            )
        }
    ) { innerPadding ->
        if (taskLists.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    stringResource(AppText.no_task_lists_text),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            LazyColumn(
                contentPadding = innerPadding,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .consumeWindowInsets(innerPadding)
            ) {
                item(key = "today") {
                    ListItem(
                        modifier = Modifier.clickable(onClick = onTodayViewClick),
                        headlineContent = { Text(text = stringResource(AppText.today_title)) },
                        leadingContent = {
                            Icon(
                                painter = painterResource(AppIcon.ic_today),
                                stringResource(AppText.today_title)
                            )
                        }
                    )
                }
                items(taskLists, key = { it.id }) { taskList ->
                    ListItem(
                        modifier = Modifier.clickable(onClick = { onViewClick(taskList) }),
                        headlineContent = { Text(text = taskList.name) },
                        leadingContent = {
                            Icon(
                                painter = painterResource(taskList.icon.drawableId),
                                stringResource(AppText.task_list_label)
                            )
                        },
                        trailingContent = {
                            IconButton(onClick = { onEditClick(taskList) }) {
                                Icon(Icons.Filled.Edit, stringResource(AppText.edit_task_list_button_label))
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun TaskListsScreenPreview() {
    JustDoItTheme {
        TaskListsScreenContent(
            taskLists = listOf(
                TaskList(
                    id = "1",
                    name = "Shopping",
                    icon = TaskList.Icon.Shopping,
                ),
                TaskList(
                    id = "2",
                    name = "Family",
                    icon = TaskList.Icon.People,
                )
            ),
            onAddClick = { },
            onEditClick = { },
            onViewClick = { },
            onProfileClick = { },
            onTodayViewClick = { },
            onSearchClick = { }
        )
    }
}