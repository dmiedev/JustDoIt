package dev.dmie.justdoit.screen.search

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dmie.justdoit.model.Task
import dev.dmie.justdoit.screen.TaskJustDoItViewModel
import dev.dmie.justdoit.service.LogService
import dev.dmie.justdoit.service.StorageService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    logService: LogService,
    storageService: StorageService
) : TaskJustDoItViewModel(logService, storageService) {
    val query = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    override val tasks: Flow<List<Task>> = combine(query.debounce(500), sort, ::Pair)
        .flatMapLatest { (query, sort) -> storageService.findTasks(query, sort) }

    fun updateQuery(queryText: String) {
        query.value = queryText
    }
}
