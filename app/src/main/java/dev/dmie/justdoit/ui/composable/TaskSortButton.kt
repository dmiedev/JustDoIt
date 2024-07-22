package dev.dmie.justdoit.ui.composable

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import dev.dmie.justdoit.R.drawable as AppIcon
import dev.dmie.justdoit.R.string as AppText
import dev.dmie.justdoit.model.Task

@Composable
fun TaskSortButton(
    selectedSort: Task.Sort,
    onSortChange: (Task.Sort) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = { expanded = true }) {
        Icon(
            painter = painterResource(id = AppIcon.ic_sort),
            contentDescription = stringResource(AppText.sort_button_label)
        )
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        for (sort in Task.Sort.entries) {
            DropdownMenuItem(
                text = {
                    Text(
                        stringResource(sort.toAppText()),
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                onClick = { onSortChange(sort); expanded = false },
                leadingIcon = {
                    if (sort == selectedSort) {
                        Icon(
                            painterResource(id = AppIcon.ic_check),
                            contentDescription = stringResource(AppText.selected_label)
                        )
                    }
                }
            )
        }
    }
}
