package com.sebiai.nutrichoicecompose.composables

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.sebiai.nutrichoicecompose.R
import com.sebiai.nutrichoicecompose.ui.theme.NutriChoiceComposeTheme

@Composable
fun FilterSearchBar(
    query: String,
    onSearch: (String) -> Unit,
    onQueryChanged: (String) -> Unit,
    onClearQuery: () -> Unit,
    onFilterClicked: () -> Unit,
    showFilterBadge: Boolean,
    modifier: Modifier = Modifier,
    hint: String = ""
) {
    Row (
        modifier = modifier.height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SearchBarImplementation(
            modifier = Modifier
                .weight(1F),
            query = query,
            hint = hint,
            onSearch = onSearch,
            onQueryChanged = onQueryChanged,
            onClearQuery = onClearQuery
        )
        Spacer(
            modifier = Modifier.width(12.dp)
        )
        FilledTonalIconButton(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1F),
            onClick = onFilterClicked
        ) {
            BadgedBox(
                badge = { if (showFilterBadge) Badge() }
            ) {
                Icon(
                    imageVector = Icons.Outlined.FilterAlt,
                    contentDescription = stringResource(R.string.content_description_filter_button_icon)
                )
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchBarImplementation(
    modifier: Modifier = Modifier,
    query: String,
    hint: String,
    onSearch: (String) -> Unit,
    onQueryChanged: (String) -> Unit,
    onClearQuery: () -> Unit
) {
    Surface (
        modifier = modifier,
        color = MaterialTheme.colorScheme.secondaryContainer,
        shape = RoundedCornerShape(100)
    ) {
        val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
        val focusManager = LocalFocusManager.current
        val textFieldFocusRequester = remember { FocusRequester() }
        val keyboardController = LocalSoftwareKeyboardController.current

        val realOnSearch: () -> Unit = {
            onSearch(query)
            focusManager.clearFocus()
        }

        BasicTextField(
            modifier = Modifier.focusRequester(textFieldFocusRequester),
            value = query,
            onValueChange = onQueryChanged,
            textStyle = MaterialTheme.typography.bodyLarge.merge(TextStyle(color = MaterialTheme.colorScheme.onSecondaryContainer)),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { realOnSearch() }),
            interactionSource = interactionSource,
            cursorBrush = SolidColor(MaterialTheme.colorScheme.onSecondaryContainer),
            decorationBox =
                @Composable { innerTextField ->
                    TextFieldDefaults.DecorationBox(
                        value = query,
                        innerTextField = innerTextField,
                        enabled = true,
                        singleLine = true,
                        visualTransformation = VisualTransformation.None,
                        interactionSource = interactionSource,
                        placeholder = { Text(hint) },
                        leadingIcon = {
                            IconButton(
                                modifier = modifier.offset(6.dp),
                                onClick = realOnSearch
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Search,
                                    contentDescription = stringResource(R.string.content_description_search_bar_search_icon)
                                )
                            }
                        },
                        trailingIcon = {
                            if (!query.isEmpty()) {
                                IconButton(
                                    modifier = modifier.offset((-6).dp),
                                    onClick = {
                                        textFieldFocusRequester.requestFocus()
                                        keyboardController?.show()
                                        onClearQuery()
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Close,
                                        contentDescription = stringResource(R.string.content_description_search_bar_clear_query_icon)
                                    )
                                }
                            }
                        },
                        container = {} // Removes underline and background stuff
                    )
                }
        )
    }
}

@PreviewLightDark
@Composable
private fun FilterSearchBarWithQueryPreview() {
    NutriChoiceComposeTheme {
        FilterSearchBar(
            query = "Query",
            onSearch = {},
            onQueryChanged = {},
            onClearQuery = {},
            onFilterClicked = {},
            showFilterBadge = true
        )
    }
}

@PreviewLightDark
@Composable
private fun FilterSearchBarOnlyHintPreview() {
    NutriChoiceComposeTheme {
        FilterSearchBar(
            query = "",
            onSearch = {},
            onQueryChanged = {},
            onClearQuery = {},
            onFilterClicked = {},
            showFilterBadge = false,
            hint = "Hint"
        )
    }
}