package dev.dmie.justdoit.screen.add_edit_task_list

import androidx.annotation.StringRes
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dmie.justdoit.TASK_LIST_ID
import dev.dmie.justdoit.model.TaskList
import dev.dmie.justdoit.screen.JustDoItViewModel
import dev.dmie.justdoit.service.LogService
import dev.dmie.justdoit.service.StorageService
import dev.dmie.justdoit.ui.snackbar.SnackbarManager
import dev.dmie.justdoit.R.string as AppText
import javax.inject.Inject

@HiltViewModel
class AddEditTaskListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val storageService: StorageService,
    logService: LogService
): JustDoItViewModel(logService) {
    val taskList = mutableStateOf(TaskList())

    @get:StringRes
    val taskListNameError by derivedStateOf {
        if (taskList.value.name.isBlank()) {
            AppText.empty_field_error_text
        } else {
            null
        }
    }

    init {
        val id = savedStateHandle.get<String>(TASK_LIST_ID)
        if (id != null) {
            launchCatching {
                taskList.value = storageService.getTaskList(id) ?: TaskList()
            }
        }
    }

    fun handleNameChange(newValue: String) {
        taskList.value = taskList.value.copy(name = newValue)
    }

    fun handleIconChange(newValue: TaskList.Icon) {
        taskList.value = taskList.value.copy(icon = newValue)
    }

    fun finish(popUp: () -> Unit) {
        if (taskListNameError != null) {
            return
        }
        launchCatching {
            val taskList = taskList.value
            if (taskList.isNew()) {
                storageService.saveTaskList(taskList)
            } else {
                storageService.updateTaskList(taskList)
            }
            popUp()
        }
    }

    fun delete(popUp: () -> Unit) {
        launchCatching {
            val taskList = taskList.value
            if (!taskList.isNew()) {
                storageService.deleteTaskList(taskList.id)
                popUp()
                SnackbarManager.showMessage(AppText.task_list_deleted_text)
            }
        }
    }
}