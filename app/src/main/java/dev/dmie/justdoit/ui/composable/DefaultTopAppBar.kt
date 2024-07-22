package dev.dmie.justdoit.ui.composable

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import dev.dmie.justdoit.ui.theme.JustDoItTheme
import dev.dmie.justdoit.R.string as AppText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTopAppBar(
    title: String,
    actions: @Composable RowScope.() -> Unit = {},
    popUp: (() -> Unit)? = null,
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            if (popUp != null) {
                IconButton(onClick = popUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(AppText.back_button_label)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        actions = actions
    )
}

@Composable
@Preview
fun TopAppBarPreview() {
    JustDoItTheme {
        DefaultTopAppBar(title = "Just Do It", actions = {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        })
    }
}