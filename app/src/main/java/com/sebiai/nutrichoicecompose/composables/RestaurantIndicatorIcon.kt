package com.sebiai.nutrichoicecompose.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sebiai.nutrichoicecompose.R
import com.sebiai.nutrichoicecompose.ui.theme.NutriChoiceComposeTheme

@Composable
fun RestaurantIndicatorIcon(
    modifier: Modifier = Modifier,
    shadowElevation: Dp = 12.dp
) {
    Box(
        modifier = modifier
    ) {
        Surface(
            modifier = Modifier
                .size(35.dp)
                .aspectRatio(1F),
            shape = RoundedCornerShape(100),
            color = MaterialTheme.colorScheme.tertiaryContainer,
            shadowElevation = shadowElevation
        ) {
            Icon(
                painter = painterResource(R.drawable.restaurant_indicator_icon),
                contentDescription = null
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun FoodCardSmallPreview() {
    NutriChoiceComposeTheme {
        RestaurantIndicatorIcon()
    }
}