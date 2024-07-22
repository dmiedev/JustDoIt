package dev.dmie.justdoit.screen.add_edit_task

import androidx.annotation.StringRes
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dmie.justdoit.TASK_ID
import dev.dmie.justdoit.TASK_LIST_ID
import dev.dmie.justdoit.model.Task
import dev.dmie.justdoit.screen.JustDoItViewModel
import dev.dmie.justdoit.service.LogService
import dev.dmie.justdoit.service.StorageService
import dev.dmie.justdoit.ui.snackbar.SnackbarManager
import dev.dmie.justdoit.ui.snackbar.SnackbarMessage
import java.util.Date
import javax.inject.Inject
import dev.dmie.justdoit.R.string as AppText

@HiltViewModel
class AddEditTaskViewModel @Inject constructor(
    private val storageService: StorageService,
    private val savedStateHandle: SavedStateHandle,
    logService: LogService,
) : JustDoItViewModel(logService) {
    val task = mutableStateOf(Task())

    @get:StringRes
    val titleError by derivedStateOf {
        if (task.value.title.isBlank()) {
            AppText.empty_field_error_text
        } else null
    }

    init {
        val taskId = savedStateHandle.get<String>(TASK_ID)
        if (taskId != null) {
            launchCatching {
                task.value = storageService.getTask(taskId) ?: Task()
            }
        }
    }

    fun handleTitleChange(newValue: String) {
        task.value = task.value.copy(title = newValue)
    }

    fun handleDescriptionChange(newValue: String) {
        task.value = task.value.copy(description = newValue)
    }

    fun handleDateChange(date: Date?) {
        task.value = task.value.copy(dueDate = date)
    }

    fun handlePriorityChange(priority: Task.Priority) {
        task.value = task.value.copy(priority = priority.ordinal)
    }

    fun delete(popUp: () -> Unit) {
        launchCatching {
            storageService.deleteTask(task.value.id)
            popUp()
            SnackbarManager.showMessage(AppText.task_deleted_text)
        }
    }

    fun finish(popUp: () -> Unit) {
        val task = task.value
        launchCatching {
            if (task.isNew()) {
                val taskListId = checkNotNull(savedStateHandle.get<String>(TASK_LIST_ID))
                storageService.saveTask(taskListId, task)
            } else {
                storageService.updateTask(task)
            }
            popUp()
        }
    }
}