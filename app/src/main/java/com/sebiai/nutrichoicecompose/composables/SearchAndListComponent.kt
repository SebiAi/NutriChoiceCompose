package com.sebiai.nutrichoicecompose.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sebiai.nutrichoicecompose.R
import com.sebiai.nutrichoicecompose.ui.theme.NutriChoiceComposeTheme

@Composable
fun SearchAndListComponent(
    modifier: Modifier = Modifier,

    welcomeMessageHeading: String? = null,
    welcomeMessageSubtitle: String? = null,

    query: String,
    onSearch: (String) -> Unit,
    onQueryChanged: (String) -> Unit,
    onClearQuery: () -> Unit,
    onFilterClicked: () -> Unit,

    listHeading: @Composable () -> Unit,
    isListEmpty: Boolean,
    emptyListInfoTitle: String,
    emptyListInfoSubtitle: String,

    listScrollState: LazyListState = rememberLazyListState(),
    content: LazyListScope.() -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        welcomeMessageHeading?.let {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = it,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
        if (welcomeMessageHeading != null && welcomeMessageSubtitle != null) {
            Spacer(modifier = Modifier.height(10.dp))
        }
        welcomeMessageSubtitle?.let {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = it,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        if (welcomeMessageHeading != null || welcomeMessageSubtitle != null) {
            Spacer(modifier = Modifier.height(12.dp))
        }

        FilterSearchBar(
            query = query,
            onSearch = onSearch,
            onQueryChanged = onQueryChanged,
            onClearQuery = onClearQuery,
            onFilterClicked = onFilterClicked,
            hint = stringResource(R.string.search_bar_hint)
        )
        Spacer(modifier = Modifier.height(12.dp))
        listHeading()
        Spacer(modifier = Modifier.height(6.dp))
        if (isListEmpty) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CenteredTitleWithSubtitle(
                    modifier = Modifier.padding(8.dp, 0.dp),
                    title = emptyListInfoTitle,
                    subtitle = emptyListInfoSubtitle,
                )
            }
        } else {
            LazyColumn (
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                state = listScrollState,
                content = content
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchAndListComponentPreview() {
    var query: String by remember { mutableStateOf("") }

    NutriChoiceComposeTheme {
        SearchAndListComponent(
            welcomeMessageHeading = "Title",
            welcomeMessageSubtitle = "Subtitle",

            query = query,
            onSearch = {},
            onQueryChanged = { query = it },
            onClearQuery = { query = "" },
            onFilterClicked = {},

            listHeading = @Composable {
                Text(
                    "Heading"
                )
            },
            isListEmpty = true,
            emptyListInfoTitle = "EmptyTitle",
            emptyListInfoSubtitle = "EmptySubtitle"
        ) {

        }
    }
}