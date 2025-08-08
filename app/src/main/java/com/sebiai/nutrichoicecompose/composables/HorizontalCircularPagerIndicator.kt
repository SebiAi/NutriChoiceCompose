package com.sebiai.nutrichoicecompose.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.sebiai.nutrichoicecompose.ui.theme.NutriChoiceComposeTheme
import kotlin.math.absoluteValue
import kotlin.math.sign

@Composable
fun HorizontalCircularPagerIndicator(
    modifier: Modifier = Modifier,

    pagerState: PagerState,
    activeColor: Color = LocalContentColor.current,
    inactiveColor: Color = activeColor.copy(alpha = 0.3F),
    indicatorSize: Dp = 8.dp
) {
    val pageCount = pagerState.pageCount
    val spacing = indicatorSize
    val indicatorSizePx = LocalDensity.current.run { indicatorSize.roundToPx() }
    val spacingPx = LocalDensity.current.run { spacing.roundToPx() }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(
                indicatorSize,
                alignment = Alignment.CenterHorizontally
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(pageCount) {
                Box(
                    modifier = Modifier
                        .background(
                            color = inactiveColor,
                            shape = CircleShape
                        )
                        .size(indicatorSize)
                )
            }
        }
        Box(
            modifier = Modifier
                .offset {
                    val position = pagerState.currentPage
                    val offset = pagerState.currentPageOffsetFraction
                    val next = pagerState.currentPage + offset.sign.toInt()

                    val scrollPosition = ((next - position) * offset.absoluteValue + position)
                        .coerceIn(
                            0f,
                            (pageCount - 1)
                                .coerceAtLeast(0)
                                .toFloat()
                        )

                    IntOffset(
                        x = ((spacingPx + indicatorSizePx) * scrollPosition).toInt(),
                        y = 0
                    )
                }
                .background(
                    color = activeColor,
                    shape = CircleShape
                )
                .size(indicatorSize)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HorizontalPagerIndicatorStaticPreview() {
    val pagerState = PagerState(pageCount = { 4 })

    NutriChoiceComposeTheme {
        HorizontalCircularPagerIndicator(
            pagerState = pagerState
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HorizontalPagerIndicatorDynamicPreview() {
    val data = listOf("Page 1", "Page 2", "Page 3", "Page 4")
    val pagerState = rememberPagerState(pageCount = data::size)

    NutriChoiceComposeTheme {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalPager(
                modifier = Modifier.height(400.dp),
                state = pagerState
            ) { index ->
                Box(
                    modifier = Modifier
                        .padding(6.dp)
                        .fillMaxSize()
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(5)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        style = MaterialTheme.typography.displaySmall,
                        text = data[index]
                    )
                }
            }
            HorizontalCircularPagerIndicator(
                pagerState = pagerState
            )
        }
    }
}