package com.sebiai.nutrichoicecompose.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sebiai.nutrichoicecompose.R
import com.sebiai.nutrichoicecompose.composables.FoodCard
import com.sebiai.nutrichoicecompose.composables.FoodCardType
import com.sebiai.nutrichoicecompose.composables.RestaurantIndicatorIcon
import com.sebiai.nutrichoicecompose.composables.TitleAndMoneyRow
import com.sebiai.nutrichoicecompose.composables.determineCustomizableChips
import com.sebiai.nutrichoicecompose.dataclasses.AFood
import com.sebiai.nutrichoicecompose.dataclasses.Ingredient
import com.sebiai.nutrichoicecompose.dataclasses.Meal
import com.sebiai.nutrichoicecompose.dataclasses.Mensa
import com.sebiai.nutrichoicecompose.dataclasses.NutritionPreferences
import com.sebiai.nutrichoicecompose.dataclasses.NutritionValues
import com.sebiai.nutrichoicecompose.ui.theme.NutriChoiceComposeTheme

// TODO: Add shared element transition for image, restaurant indicator, title and money: https://developer.android.com/develop/ui/compose/animation/shared-elements

@Composable
fun FoodDetailScreen(
    food: AFood,
    onFoodCardClicked: (AFood, NutritionPreferences) -> Unit,
    nutritionPreferences: NutritionPreferences,
    modifier: Modifier = Modifier
) {
    val isRestaurantFood: Boolean = food is Meal

    LazyColumn {
        // Image
        item {
            ImageContent(
                image = food.getImage(LocalContext.current),
                showRestaurantIndicatorIcon = isRestaurantFood
            )
        }
        // Main content
        item {
            Column (
                modifier = Modifier.padding(16.dp)
            ) {
                TitleAndMoneyRow(
                    title = food.title,
                    priceString = food.getPriceString(LocalContext.current),
                    fontScale = 1.3
                )
                if (isRestaurantFood) {
                    val meal: Meal = food
                    Text(
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 24.dp),
                        text = meal.restaurantName,
                        fontStyle = FontStyle.Italic
                    )
                }
                Spacer(
                    modifier = Modifier.height(12.dp)
                )
                NutritionTable(
                    nutritionValues = food.nutritionValues
                )
                Spacer(modifier = Modifier.height(12.dp))
                Column(
                    modifier = Modifier.padding(8.dp, 0.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Image(
                            modifier = Modifier.height(80.dp).weight(1F),
                            imageVector = food.getNutriScoreImage(LocalContext.current),
                            contentScale = ContentScale.Fit,
                            contentDescription = stringResource(R.string.nutri_score_name)
                        )
                        Image(
                            modifier = Modifier.height(80.dp).weight(1F),
                            imageVector = food.getGreenScoreImage(LocalContext.current),
                            contentScale = ContentScale.Fit,
                            contentDescription = stringResource(R.string.green_score_name)
                        )
                    }
                }
            }
        }

        // Content for meals (ingredients)
        if (isRestaurantFood) {
            val contentHorizontalPaddingModifier: Modifier = Modifier.padding(16.dp, 0.dp)
            item {
                Column(
                    modifier = contentHorizontalPaddingModifier
                ) {
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        style = MaterialTheme.typography.titleLarge,
                        text = stringResource(R.string.ingredients_heading),
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
            items(food.ingredients) {
                FoodCard(
                    modifier = contentHorizontalPaddingModifier.clickable(
                        onClick = { onFoodCardClicked(it, nutritionPreferences) }
                    ),
                    type = FoodCardType.SMALL,
                    image = it.getImage(LocalContext.current),
                    title = it.title,
                    priceString = it.getPriceString(LocalContext.current),
                    isRestaurantFood = it is Meal,
                    customizableChips = determineCustomizableChips(LocalContext.current, it, nutritionPreferences)
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
private fun ImageContent(
    image: ImageBitmap,
    showRestaurantIndicatorIcon: Boolean,

    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        contentAlignment = Alignment.TopEnd
    ) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            alignment = Alignment.Center,
            bitmap = image,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        if (showRestaurantIndicatorIcon) {
            RestaurantIndicatorIcon(
                modifier = Modifier
                    .size(48.dp)
                    .offset((-12).dp, (12).dp)
            )
        }
    }
}

@Composable
private fun NutritionTable(
    nutritionValues: NutritionValues,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.secondaryContainer,
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.onBackground)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                style = MaterialTheme.typography.labelMedium,
                text = stringResource(R.string.per_100_unit)
            )

            NutritionTableRow(
                modifier = Modifier.fillMaxWidth()
            ) {
                NutritionTableElement(
                    heading = stringResource(R.string.nutrient_type_calories),
                    icon = painterResource(R.drawable.nutrition_kcal_icon),
                    value = nutritionValues.calories,
                    unit = stringResource(R.string.nutrient_energy_unit)
                )
                NutritionTableElement(
                    heading = stringResource(R.string.nutrient_type_protein),
                    icon = painterResource(R.drawable.nutrition_protein_icon),
                    value = nutritionValues.protein,
                    unit = stringResource(R.string.nutrient_weight_unit)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            NutritionTableRow(
                modifier = Modifier.fillMaxWidth()
            ) {
                NutritionTableElement(
                    heading = stringResource(R.string.nutrient_type_fat),
                    icon = painterResource(R.drawable.nutrition_oil_icon),
                    value = nutritionValues.fat,
                    unit = stringResource(R.string.nutrient_weight_unit)
                )
                NutritionTableElement(
                    heading = stringResource(R.string.nutrient_type_carbs),
                    icon = painterResource(R.drawable.nutrition_carbs_icon),
                    value = nutritionValues.carbs,
                    unit = stringResource(R.string.nutrient_weight_unit)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            NutritionTableRow(
                modifier = Modifier.fillMaxWidth()
            ) {
                NutritionTableElement(
                    heading = stringResource(R.string.nutrient_type_sugar),
                    icon = painterResource(R.drawable.nutrition_sugar_icon),
                    value = nutritionValues.sugar,
                    unit = stringResource(R.string.nutrient_weight_unit)
                )
                NutritionTableElement(
                    heading = stringResource(R.string.nutrient_type_salt),
                    icon = painterResource(R.drawable.nutrition_salt_icon),
                    value = nutritionValues.salt,
                    unit = stringResource(R.string.nutrient_weight_unit)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
@Composable
private fun NutritionTableRow(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        content = content
    )
}
@Composable
private fun NutritionTableElement(
    heading: String,
    icon: Painter,
    value: Double,
    unit: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            style = MaterialTheme.typography.titleLarge,
            text = heading
        )
        Row {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = icon,
                contentDescription = null
            )
            Text(
                style = MaterialTheme.typography.titleLarge,
                fontSize = 20.sp,
                text = "$value $unit"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FoodDetailScreenWithIngredientPreview() {
    NutriChoiceComposeTheme {
        FoodDetailScreen(
            food = Ingredient(
                title = "Title",
                imageResource = R.drawable.tomato_paste_image,
                nutritionValues = NutritionValues(),
                price = AFood.Price.MEDIUM,
                nutriScore = AFood.Score.C,
                greenScore = AFood.Score.C,
                dietaryPreferences = AFood.DietaryPreferences.VEGAN
            ),
            onFoodCardClicked = {food, nutritionPreferences -> },
            nutritionPreferences = NutritionPreferences(true, true, true, true, true, true, true, true),
            modifier = Modifier
                .fillMaxSize()
        )
    }
}
@Preview(showBackground = true)
@Composable
private fun FoodDetailScreenWithMealPreview() {
    NutriChoiceComposeTheme {
        FoodDetailScreen(
            food = Meal(
                title = "Title",
                imageResource = R.drawable.wiener_schnitzl_image,
                nutritionValues = NutritionValues(),
                price = AFood.Price.MEDIUM,
                nutriScore = AFood.Score.B,
                greenScore = AFood.Score.D,
                dietaryPreferences = AFood.DietaryPreferences.VEGAN,
                ingredients = listOf(
                    Ingredient(
                        title = "Title",
                        imageResource = R.drawable.tomato_paste_image,
                        nutritionValues = NutritionValues(),
                        price = AFood.Price.MEDIUM,
                        nutriScore = AFood.Score.C,
                        greenScore = AFood.Score.C,
                        dietaryPreferences = AFood.DietaryPreferences.VEGAN
                    )
                ),
                restaurant = Mensa("Mensa Uni Wien")
            ),
            onFoodCardClicked = {food, nutritionPreferences -> },
            nutritionPreferences = NutritionPreferences(true, true, true, true, true, true, true, true),
            modifier = Modifier
                .fillMaxSize()
        )
    }
}