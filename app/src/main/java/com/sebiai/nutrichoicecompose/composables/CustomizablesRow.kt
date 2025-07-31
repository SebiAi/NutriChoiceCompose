package com.sebiai.nutrichoicecompose.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sebiai.nutrichoicecompose.ui.theme.NutriChoiceComposeTheme

@Composable
fun CustomizablesRow(
    customizableChips: List<String>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items = customizableChips) {
            SuggestionChip(
                onClick = {},
                label = {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CustomizablesRowPreview() {
    NutriChoiceComposeTheme {
        CustomizablesRow(
            customizableChips = listOf("Customizable1", "Customizable2", "Customizable3")
        )
    }
}