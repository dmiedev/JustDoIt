package dev.dmie.justdoit.screen.task_lists

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dmie.justdoit.ADD_EDIT_TASK_LIST_SCREEN
import dev.dmie.justdoit.PROFILE_SCREEN
import dev.dmie.justdoit.SEARCH_SCREEN
import dev.dmie.justdoit.TASKS_SCREEN
import dev.dmie.justdoit.TASK_LIST_ID
import dev.dmie.justdoit.TODAY_TASKS_SCREEN
import dev.dmie.justdoit.model.TaskList
import dev.dmie.justdoit.screen.JustDoItViewModel
import dev.dmie.justdoit.service.LogService
import dev.dmie.justdoit.service.StorageService
import javax.inject.Inject

@HiltViewModel
class TaskListsViewModel @Inject constructor(
    logService: LogService,
    storageService: StorageService
) : JustDoItViewModel(logService) {
    val taskLists = storageService.taskLists

    fun addTaskList(openScreen: (String) -> Unit) = openScreen(ADD_EDIT_TASK_LIST_SCREEN)

    fun editTaskList(openScreen: (String) -> Unit, taskList: TaskList) {
        openScreen("$ADD_EDIT_TASK_LIST_SCREEN?$TASK_LIST_ID=${taskList.id}")
    }

    fun viewTaskList(openScreen: (String) -> Unit, taskList: TaskList) {
        openScreen("$TASKS_SCREEN/${taskList.id}")
    }

    fun openProfile(openScreen: (String) -> Unit) = openScreen(PROFILE_SCREEN)

    fun viewTodayTaskList(openScreen: (String) -> Unit) = openScreen(TODAY_TASKS_SCREEN)

    fun openSearch(openScreen: (String) -> Unit) = openScreen(SEARCH_SCREEN)
}