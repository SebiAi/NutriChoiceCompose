package com.sebiai.nutrichoicecompose.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.sebiai.nutrichoicecompose.R
import com.sebiai.nutrichoicecompose.ui.theme.NutriChoiceComposeTheme

@Composable
fun FoodCardBig(
    image: ImageBitmap,
    title: String,
    priceString: String,
    isRestaurantFood: Boolean,
    customizableChips: List<String>,

    modifier: Modifier = Modifier
) {
    Card (
        modifier = modifier
    ) {
        Column (
            modifier = Modifier.fillMaxWidth(),
        ) {
            Box(
                modifier = Modifier.fillMaxWidth().height(200.dp),
                contentAlignment = Alignment.TopEnd
            ) {
                Image(
                    modifier = Modifier.fillMaxWidth(),
                    alignment = Alignment.Center,
                    bitmap = image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                if (isRestaurantFood) {
                    Box(
                        modifier = Modifier.offset((-12).dp, (12).dp)
                    ) {
                        Surface(
                            modifier = Modifier
                                .size(35.dp)
                                .aspectRatio(1F),
                            shape = RoundedCornerShape(100),
                            color = MaterialTheme.colorScheme.tertiaryContainer,
                            shadowElevation = 12.dp
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.restaurant_indicator_icon),
                                contentDescription = null
                            )
                        }
                    }
                }
            }

            Column (
                modifier = Modifier.padding(10.dp)
            ) {
                Row {
                    Text(
                        modifier = Modifier.weight(1F),
                        style = MaterialTheme.typography.bodyLarge,
                        text = title,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        style = MaterialTheme.typography.labelLarge,
                        text = priceString,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic
                    )
                }
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(items = customizableChips) {
                        SuggestionChip(
                            onClick = {},
                            label = { Text(text = it, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold) }
                        )
                    }
                }
            }
        }
    }
}

private data class FoodCardPreviewData(val title: String, val isRestaurantFood: Boolean, val customizables: List<String>)
private class FoodCardBigProvider: PreviewParameterProvider<FoodCardPreviewData> {
    override val values: Sequence<FoodCardPreviewData> = listOf(
        FoodCardPreviewData("Title", true, listOf("Customizable1", "Customizable2")),
        FoodCardPreviewData(
            "This is a very long title that will hopefully be to long for the card for real now this is important for testing",
            false, listOf("Customizable1", "Customizable2", "Customizable3", "Customizable4")
        ),
    ).asSequence()

}

@PreviewLightDark
@Composable
private fun FoodCardBigPreview(
    @PreviewParameter(FoodCardBigProvider::class)
    data: FoodCardPreviewData
) {
    NutriChoiceComposeTheme {
        FoodCardBig(
            image = ImageBitmap.imageResource(R.drawable.tomato_paste_image),
            title = data.title,
            priceString = "$$$",
            isRestaurantFood = data.isRestaurantFood,
            customizableChips = data.customizables
        )
    }
}