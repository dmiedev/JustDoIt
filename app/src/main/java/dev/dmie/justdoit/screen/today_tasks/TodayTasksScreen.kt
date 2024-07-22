package dev.dmie.justdoit.screen.today_tasks

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.dmie.justdoit.model.Task
import dev.dmie.justdoit.screen.tasks.TasksScreenContent

@Composable
fun TodayTasksScreen(
    openScreen: (String) -> Unit,
    popUp: () -> Unit,
    viewModel: TodayTasksViewModel = hiltViewModel()
) {
    val tasks by viewModel.tasks.collectAsStateWithLifecycle(initialValue = emptyList())
    val sort by viewModel.sort.collectAsStateWithLifecycle(Task.Sort.None)

    TasksScreenContent(
        taskListName = "Today",
        tasks = tasks,
        sort = sort,
        onEditClick = { viewModel.editTask(openScreen, it) },
        onCheckChange = viewModel::checkTask,
        onDeleteClick = viewModel::deleteTask,
        onSortChange = viewModel::setSort,
        popUp = popUp
    )
}