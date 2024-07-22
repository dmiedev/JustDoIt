package dev.dmie.justdoit.screen.tasks

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dmie.justdoit.TASK_LIST_ID
import dev.dmie.justdoit.model.TaskList
import dev.dmie.justdoit.screen.TaskJustDoItViewModel
import dev.dmie.justdoit.service.LogService
import dev.dmie.justdoit.service.StorageService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    storageService: StorageService,
    savedStateHandle: SavedStateHandle,
    logService: LogService
) : TaskJustDoItViewModel(logService, storageService) {
    private val taskListId = checkNotNull(savedStateHandle.get<String>(TASK_LIST_ID))
    val taskList = mutableStateOf(TaskList())

    @OptIn(ExperimentalCoroutinesApi::class)
    override val tasks = sort.flatMapLatest { storageService.getTasks(taskListId, it) }

    init {
        launchCatching {
            taskList.value = storageService.getTaskList(taskListId) ?: TaskList()
        }
    }

    fun addTask(openScreen: (String) -> Unit) = super.addTask(openScreen, taskListId)
}

