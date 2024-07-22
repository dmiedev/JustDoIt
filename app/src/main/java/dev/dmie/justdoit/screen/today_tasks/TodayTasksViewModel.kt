package dev.dmie.justdoit.screen.today_tasks

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dmie.justdoit.screen.TaskJustDoItViewModel
import dev.dmie.justdoit.service.LogService
import dev.dmie.justdoit.service.StorageService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class TodayTasksViewModel @Inject constructor(
    storageService: StorageService,
    logService: LogService
) : TaskJustDoItViewModel(logService, storageService) {
    @OptIn(ExperimentalCoroutinesApi::class)
    override val tasks = sort.flatMapLatest { storageService.getTodayTasks(it) }
}
