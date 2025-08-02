package com.sebiai.nutrichoicecompose.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.OpenInNew
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.sebiai.nutrichoicecompose.R
import com.sebiai.nutrichoicecompose.ui.theme.NutriChoiceComposeTheme

@Composable
fun ScoreInfoDialog(
    modifier: Modifier = Modifier,

    title: String,
    scoreGeneralExplanation: String,
    currentScoreExplanation: String,
    currentScoreImage: ImageVector,
    currentScoreImageContentDescription: String?,

    onDismiss: () -> Unit,
    onLearnMore: () -> Unit
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        title = { Text(text = title) },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = scoreGeneralExplanation
                )
                HorizontalDivider()
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = currentScoreExplanation
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Image(
                        modifier = Modifier
                            .height(80.dp),
                        imageVector = currentScoreImage,
                        contentDescription = currentScoreImageContentDescription,
                        contentScale = ContentScale.Fit
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = onDismiss
            ) { Text(text = stringResource(R.string.ok_confirmation)) }
        },
        dismissButton = {
            TextButton(
                onClick = onLearnMore
            ) {
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = stringResource(R.string.learn_more))
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        modifier = Modifier.size(18.dp),
                        imageVector = Icons.AutoMirrored.Outlined.OpenInNew,
                        contentDescription = null
                    )
                }
            }
        }
    )
}

@PreviewLightDark
@Composable
private fun ScoreInfoDialogPreview() {
    NutriChoiceComposeTheme {
        ScoreInfoDialog(
            title = "Title",
            scoreGeneralExplanation = "Score explanation here",
            currentScoreExplanation = "Current score explanation here",
            currentScoreImage = ImageVector.vectorResource(R.drawable.nutriscore_a_new_en),
            currentScoreImageContentDescription = stringResource(R.string.nutri_score_name),

            onDismiss = {},
            onLearnMore = {}
        )
    }
}