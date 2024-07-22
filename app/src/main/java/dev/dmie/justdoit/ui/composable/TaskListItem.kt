package dev.dmie.justdoit.ui.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import dev.dmie.justdoit.R.drawable as AppIcon
import dev.dmie.justdoit.R.string as AppText
import dev.dmie.justdoit.model.Task

@Composable
fun TaskListItem(
    task: Task,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onCheckChange: () -> Unit
) {
    ListItem(
        headlineContent = {
            Row {
                Text(
                    text = task.title,
                    textDecoration = if (task.completed)
                        TextDecoration.LineThrough
                    else
                        TextDecoration.None
                )
                for (i in 0 until task.priority) {
                    Icon(
                        painter = painterResource(AppIcon.ic_priority),
                        contentDescription = stringResource(AppText.priority_label),
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }
            }
        },
        supportingContent = {
            if (task.description.isNotBlank()) {
                Text(text = task.description)
            }
        },
        overlineContent = {
            if (task.dueDate != null) {
                Text(text = dateFormat.format(task.dueDate))
            }
        },
        leadingContent = {
            Checkbox(
                checked = task.completed,
                onCheckedChange = { onCheckChange() },
                modifier = Modifier.padding(8.dp, 0.dp)
            )
        },
        trailingContent = {
            if (task.completed) {
                IconButton(onClick = onDeleteClick) {
                    Icon(Icons.Filled.Delete, stringResource(AppText.delete_button_label))
                }
            } else {
                IconButton(onClick = onEditClick) {
                    Icon(Icons.Filled.Edit, stringResource(AppText.edit_task_label))
                }
            }
        }
    )
}
