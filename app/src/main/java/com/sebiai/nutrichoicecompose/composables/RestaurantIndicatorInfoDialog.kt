package com.sebiai.nutrichoicecompose.composables

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.sebiai.nutrichoicecompose.R
import com.sebiai.nutrichoicecompose.ui.theme.NutriChoiceComposeTheme

@Composable
fun RestaurantIndicatorInfoDialog(
    modifier: Modifier = Modifier,

    onDismiss: () -> Unit
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(R.string.restaurant_indicator_icon_tooltip)) },
        text = { Text(text = stringResource(R.string.restaurant_indicator_description)) },
        confirmButton = {
            TextButton(
                onClick = onDismiss
            ) { Text(text = stringResource(R.string.ok_confirmation)) }
        }
    )
}

@PreviewLightDark
@Composable
private fun RestaurantIndicatorInfoDialogPreview() {
    NutriChoiceComposeTheme {
        RestaurantIndicatorInfoDialog(
            onDismiss = {}
        )
    }
}