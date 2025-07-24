package com.sebiai.nutrichoicecompose.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.sebiai.nutrichoicecompose.ui.theme.NutriChoiceComposeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    titleText: String,
    showBackArrow: Boolean,
    onBackArrowPressed: () -> Unit,
    showSettingsAction: Boolean = false,
    onSettingsActionClick: () -> Unit = {}
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        title = {
            Text(
                text = titleText
            )
        },
        navigationIcon = {
            if (showBackArrow) {
                IconButton(
                    onClick = onBackArrowPressed
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                        contentDescription = "Back Arrow"
                    )
                }
            }
        },
        actions = {
            if (showSettingsAction) {
                IconButton(
                    onClick = onSettingsActionClick
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Settings,
                        contentDescription = "Settings"
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun MyTopAppBarDefaultPreview() {
    NutriChoiceComposeTheme {
        MyTopAppBar(
            titleText = "AppBar",
            false,
            {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun MyTopAppBarWithSettingsAndBackArrowActionPreview() {
    NutriChoiceComposeTheme {
        MyTopAppBar(
            titleText = "AppBar",
            true,
            {},
            true
        )
    }
}