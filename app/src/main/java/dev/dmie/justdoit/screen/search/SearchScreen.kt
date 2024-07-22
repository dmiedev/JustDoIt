package dev.dmie.justdoit.screen.search

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
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

@Composable
fun SearchScreen(
    popUp: () -> Unit,
    openScreen: (String) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val tasks by viewModel.tasks.collectAsStateWithLifecycle(emptyList())
    val query by viewModel.query.collectAsStateWithLifecycle("")
    val sort by viewModel.sort.collectAsStateWithLifecycle(Task.Sort.None)

    SearchScreenContent(
        tasks = tasks,
        query = query,
        sort = sort,
        onEditClick = { viewModel.editTask(openScreen, it) },
        onCheckChange = viewModel::checkTask,
        onDeleteClick = viewModel::deleteTask,
        onSortChange = viewModel::setSort,
        onQueryChange = viewModel::updateQuery,
        popUp = popUp,
    )
}

@Composable
fun SearchScreenContent(
    tasks: List<Task>,
    query: String,
    sort: Task.Sort,
    onEditClick: (Task) -> Unit,
    onDeleteClick: (Task) -> Unit,
    onCheckChange: (Task) -> Unit,
    onQueryChange: (String) -> Unit,
    onSortChange: (Task.Sort) -> Unit,
    popUp: () -> Unit,
) {
    Scaffold(
        topBar = {
            DefaultTopAppBar(
                title = stringResource(id = AppText.search_title),
                popUp = popUp,
                actions = { TaskSortButton(sort, onSortChange) }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .consumeWindowInsets(innerPadding)
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = innerPadding
        ) {
            item("Query field") {
                OutlinedTextField(
                    modifier = Modifier.padding(vertical = 16.dp),
                    value = query,
                    onValueChange = onQueryChange,
                    label = { Text(text = stringResource(id = AppText.query_label)) },
                    placeholder = { Text(text = stringResource(id = AppText.query_placeholder)) },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    trailingIcon = {
                        if (query.isNotEmpty()) {
                            IconButton(onClick = { onQueryChange("") }) {
                                Icon(Icons.Filled.Clear, stringResource(id = AppText.clear_button_label))
                            }
                        }
                    }
                )
            }
            items(tasks, key = { it.id }) { task ->
                TaskListItem(
                    task = task,
                    onEditClick = { onEditClick(task) },
                    onDeleteClick = { onDeleteClick(task) },
                    onCheckChange = { onCheckChange(task) },
                )
            }
        }
    }
}

@Composable
@Preview
fun SearchScreenPreview() {
    JustDoItTheme {
        SearchScreenContent(
            tasks = listOf(Task(title = "Example")),
            query = "Example",
            sort = Task.Sort.None,
            onEditClick = {},
            onDeleteClick = {},
            onCheckChange = {},
            onQueryChange = {},
            onSortChange = {},
            popUp = {}
        )
    }
}