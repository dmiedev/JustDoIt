
package dev.dmie.justdoit.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.dmie.justdoit.ADD_EDIT_TASK_SCREEN
import dev.dmie.justdoit.TASK_ID
import dev.dmie.justdoit.TASK_LIST_ID
import dev.dmie.justdoit.model.Task
import dev.dmie.justdoit.service.LogService
import dev.dmie.justdoit.service.StorageService
import dev.dmie.justdoit.ui.snackbar.SnackbarManager
import dev.dmie.justdoit.ui.snackbar.SnackbarMessage
import dev.dmie.justdoit.ui.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import dev.dmie.justdoit.R.string as AppText
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

open class JustDoItViewModel(
    private val logService: LogService
) : ViewModel() {
    fun launchCatching(
        showErrorSnackbar: Boolean = true,
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        return viewModelScope.launch(
            block = block,
            context = CoroutineExceptionHandler { _, throwable ->
                if (showErrorSnackbar) {
                    SnackbarManager.showMessage(throwable.toSnackbarMessage())
                }
                logService.logThrowable(throwable)
            },
        )
    }
}

abstract class TaskJustDoItViewModel(
    logService: LogService,
    private val storageService: StorageService
) : JustDoItViewModel(logService) {

    abstract val tasks: Flow<List<Task>>
    val sort = MutableStateFlow(Task.Sort.None)

    fun setSort(sort: Task.Sort) {
        this.sort.value = sort
    }

    fun checkTask(task: Task) {
        launchCatching {
            storageService.updateTask(task.copy(completed = !task.completed))
        }
    }

    fun addTask(openScreen: (String) -> Unit, taskListId: String) {
        openScreen("$ADD_EDIT_TASK_SCREEN?$TASK_LIST_ID=$taskListId")
    }

    fun editTask(openScreen: (String) -> Unit, task: Task) {
        openScreen("$ADD_EDIT_TASK_SCREEN?$TASK_ID=${task.id}")
    }

    fun deleteTask(task: Task) {
        launchCatching {
            storageService.deleteTask(task.id)
            SnackbarManager.showMessage(AppText.task_deleted_text)
        }
    }
}
