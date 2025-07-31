package com.sebiai.nutrichoicecompose.dataclasses

import com.sebiai.nutrichoicecompose.R

import me.xdrop.fuzzywuzzy.FuzzySearch
import me.xdrop.fuzzywuzzy.model.BoundExtractedResult
import java.util.Collections
import java.util.stream.Collectors

object Data {
    private val testFoodSet: MutableList<AFood> = mutableListOf()

    init {
        val lasagneNutrients = NutritionValues(
            calories = 145.0, protein = 5.7, fat = 7.5,
            carbs = 13.3, salt = 0.0, sugar = 0.0
        )
        val schnitzelNutrients = NutritionValues(
            calories = 145.0, protein = 55.7, fat = 17.5,
            carbs = 13.3, salt = 10.0, sugar = 4.0
        )
        val potatoSaladNutrients = NutritionValues(
            calories = 455.0, protein = 1.6, fat = 4.6,
            carbs = 14.6, salt = 1.3, sugar = 3.2
        )
        val dumplingNutrients = NutritionValues(
            calories = 519.0, protein = 1.6, fat = 3.8,
            carbs = 22.0, salt = 0.1, sugar = 0.7
        )
        val carbonaraNutrients = NutritionValues(
            calories = 206.0, protein = 5.0, fat = 12.0,
            carbs = 18.0, salt = 1.0, sugar = 1.0
        )
        val margheritaNutrients = NutritionValues(
            calories = 876.0, protein = 8.5, fat = 5.0,
            carbs = 31.0, salt = 1.0, sugar = 3.3
        )


        val tomatoPasteNutrients = NutritionValues(
            calories = 113.0, protein = 5.3, fat = 0.6,
            carbs = 17.0, salt = 5.3, sugar = 13.0
        )
        val beefNutrients = NutritionValues(
            calories = 288.0, protein = 26.33, fat = 19.54,
            carbs = 0.0, salt = 0.96, sugar = 0.0
        )
        val porkNutrients = NutritionValues(
            calories = 129.0, protein = 20.0, fat = 5.0,
            carbs = 0.0, salt = 0.2, sugar = 0.0
        )
        val carrotsNutrients = NutritionValues(
            calories = 25.0, protein = 1.0, fat = 0.2,
            carbs = 4.8, salt = 0.0, sugar = 0.0
        )
        val wheatNutrients = NutritionValues(
            calories = 338.0, protein = 11.0, fat = 1.1,
            carbs = 69.0, salt = 0.01, sugar = 0.5
        )
        val milkNutrients = NutritionValues(
            calories = 65.0, protein = 3.2, fat = 3.5,
            carbs = 4.8, salt = 0.1, sugar = 4.8
        )
        val saltNutrients = NutritionValues(
            calories = 0.0, protein = 0.0, fat = 0.0,
            carbs = 0.0, salt = 99.9, sugar = 0.0
        )
        val onionNutrients = NutritionValues(
            calories = 40.0, protein = 1.1, fat = 0.1,
            carbs = 1.2, salt = 0.001, sugar = 4.0
        )
        val vinegarNutrients = NutritionValues(
            calories = 20.0, protein = 0.4, fat = 0.0,
            carbs = 1.0, salt = 0.001, sugar = 1.0
        )
        val potatoNutrients = NutritionValues(
            calories = 73.0, protein = 2.0, fat = 0.01,
            carbs = 16.0, salt = 0.05, sugar = 1.0
        )
        val breadcrumbsNutrients = NutritionValues(
            calories = 358.0, protein = 10.0, fat = 2.0,
            carbs = 74.0, salt = 1.0, sugar = 4.0
        )
        val parsleyNutrients = NutritionValues(
            calories = 53.0, protein = 4.0, fat = 0.4,
            carbs = 7.0, salt = 0.2, sugar = 5.0
        )
        val baconNutrients = NutritionValues(
            calories = 153.0, protein = 21.0, fat = 8.0,
            carbs = 0.0, salt = 0.12, sugar = 0.0
        )
        val spaghettiNutrients = NutritionValues(
            calories = 1523.0, protein = 13.0, fat = 2.0,
            carbs = 71.0, salt = 0.01, sugar = 3.5
        )
        val eggNutrients = NutritionValues(
            calories = 154.0, protein = 13.0, fat = 11.0,
            carbs = 1.0, salt = 0.32, sugar = 1.0
        )
        val mozzarellaNutrients = NutritionValues(
            calories = 290.0, protein = 19.0, fat = 23.0,
            carbs = 2.0, salt = 0.5, sugar = 0.0
        )
        val tomatoNutrients = NutritionValues(
            calories = 17.0, protein = 1.0, fat = 0.23,
            carbs = 3.0, salt = 0.03, sugar = 3.0
        )


        val tomatoPaste = Ingredient("Tomato paste", R.drawable.tomato_paste_image, tomatoPasteNutrients, AFood.Price.LOW, AFood.Score.C, AFood.Score.C, AFood.DietaryPreferences.VEGAN)
        val beef = Ingredient("Beef", R.drawable.beef_image, beefNutrients, AFood.Price.MEDIUM, AFood.Score.B, AFood.Score.E, AFood.DietaryPreferences.NONE)
        val pork = Ingredient("Pork", R.drawable.pork_image, porkNutrients, AFood.Price.MEDIUM, AFood.Score.C, AFood.Score.E, AFood.DietaryPreferences.NONE)
        val carrots = Ingredient("Carrots", R.drawable.carrots_image, carrotsNutrients, AFood.Price.LOW, AFood.Score.A, AFood.Score.NA, AFood.DietaryPreferences.VEGAN)
        val flour = Ingredient("Wheat Flour", R.drawable.wheat_flour_image, wheatNutrients, AFood.Price.LOW, AFood.Score.A, AFood.Score.A, AFood.DietaryPreferences.VEGAN)
        val milk = Ingredient("Whole Milk", R.drawable.whole_milk_image, milkNutrients, AFood.Price.LOW, AFood.Score.C, AFood.Score.D, AFood.DietaryPreferences.VEGETARIAN)
        val salt = Ingredient("Salt", R.drawable.salt_iamge, saltNutrients, AFood.Price.LOW, AFood.Score.NA, AFood.Score.A, AFood.DietaryPreferences.VEGAN)
        val onion = Ingredient("Onion", R.drawable.onion_image, onionNutrients, AFood.Price.LOW, AFood.Score.NA, AFood.Score.NA, AFood.DietaryPreferences.VEGAN)
        val vinegar = Ingredient("Vinegar", R.drawable.vinegar_image, vinegarNutrients, AFood.Price.LOW, AFood.Score.NA, AFood.Score.NA, AFood.DietaryPreferences.VEGAN)
        val potato = Ingredient("Potato", R.drawable.potato_image, potatoNutrients, AFood.Price.LOW, AFood.Score.C, AFood.Score.D, AFood.DietaryPreferences.VEGAN)
        val dumplingBread = Ingredient("Dumpling bread", R.drawable.dumpling_bread_image, breadcrumbsNutrients, AFood.Price.LOW, AFood.Score.NA, AFood.Score.NA, AFood.DietaryPreferences.VEGETARIAN)
        val parsley = Ingredient("Parsley", R.drawable.parsley_image, parsleyNutrients, AFood.Price.LOW, AFood.Score.NA, AFood.Score.NA, AFood.DietaryPreferences.VEGAN)
        val bacon = Ingredient("Bacon", R.drawable.bacon_image, baconNutrients, AFood.Price.LOW, AFood.Score.NA, AFood.Score.NA, AFood.DietaryPreferences.NONE)
        val spaghetti = Ingredient("Spaghetti", R.drawable.spaghetti_image, spaghettiNutrients, AFood.Price.LOW, AFood.Score.A, AFood.Score.B, AFood.DietaryPreferences.VEGETARIAN)
        val egg = Ingredient("Egg", R.drawable.egg_image, eggNutrients, AFood.Price.LOW, AFood.Score.NA, AFood.Score.NA, AFood.DietaryPreferences.VEGETARIAN)
        val mozzarella = Ingredient("Mozzarella", R.drawable.mozzarella_image, mozzarellaNutrients, AFood.Price.LOW, AFood.Score.NA, AFood.Score.NA, AFood.DietaryPreferences.VEGETARIAN)
        val tomato = Ingredient("Tomato", R.drawable.tomato_image, tomatoNutrients, AFood.Price.LOW, AFood.Score.NA, AFood.Score.NA, AFood.DietaryPreferences.VEGAN)

        testFoodSet.apply {
            add(tomatoPaste)
            add(beef)
            add(pork)
            add(carrots)
            add(flour)
            add(milk)
            add(salt)
            add(onion)
            add(vinegar)
            add(potato)
            add(dumplingBread)
            add(parsley)
            add(bacon)
            add(spaghetti)
            add(egg)
            add(mozzarella)
            add(tomato)
        }

        val lasagneIngredients: List<AFood> = listOf(tomatoPaste, beef, pork, carrots, flour, milk, salt)
        val schnitzelIngredients: List<AFood> = listOf(pork, flour, salt)
        val potatoSaladIngredients: List<AFood> = listOf(vinegar, onion, salt, potato)
        val dumplingIngredients: List<AFood> = listOf(parsley, dumplingBread, salt, bacon)
        val carbonaraIngredients: List<AFood> = listOf(egg, spaghetti, salt, bacon)
        val margheritaIngredients: List<AFood> = listOf(flour, tomato, salt, mozzarella)

        val uniWien = Mensa("Mensa Uni Wien")
        val pfarrwirt = Mensa("Pfarrwirt")

        testFoodSet.apply {
            add(Meal("Lasagne", R.drawable.lasagna_image, lasagneNutrients, AFood.Price.MEDIUM, AFood.Score.C, AFood.Score.D, AFood.DietaryPreferences.NONE, lasagneIngredients, uniWien))
            add(Meal("Wiener Schnitzel", R.drawable.wiener_schnitzl_image, schnitzelNutrients, AFood.Price.MEDIUM, AFood.Score.B, AFood.Score.D, AFood.DietaryPreferences.NONE, schnitzelIngredients, pfarrwirt))
            add(Meal("Potato salad", R.drawable.potato_salad_image, potatoSaladNutrients, AFood.Price.MEDIUM, AFood.Score.B, AFood.Score.D, AFood.DietaryPreferences.VEGETARIAN, potatoSaladIngredients, pfarrwirt))
            add(Meal("Bacon dumpling", R.drawable.bacon_dumpling_image, dumplingNutrients, AFood.Price.MEDIUM, AFood.Score.C, AFood.Score.NA, AFood.DietaryPreferences.NONE, dumplingIngredients, pfarrwirt))
            add(Meal("Spaghetti alla carbonara", R.drawable.carbonara_image, carbonaraNutrients, AFood.Price.MEDIUM, AFood.Score.C, AFood.Score.C, AFood.DietaryPreferences.NONE, carbonaraIngredients, uniWien))
            add(Meal("Pizza margherita", R.drawable.margherita_image, margheritaNutrients, AFood.Price.MEDIUM, AFood.Score.C, AFood.Score.A, AFood.DietaryPreferences.VEGETARIAN, margheritaIngredients, uniWien))
        }
    }

    private fun applyFilter(foods: List<AFood>, filters: FilterState): List<AFood> {
        /*
         * Filter explained
         *
         * +---+------------------+-------------+------+
         * | i | filter.isVegan() | x.isVegan() | pass |
         * +---+------------------+-------------+------+
         * | 1 | F                | F           | T    |
         * | 2 | F                | T           | T    |
         * | 3 | T                | F           | F    |
         * | 4 | T                | T           | T    |
         * +---+------------------+-------------+------+
         *
         * For 1 & 2: !filter.isVegan()
         * For 3 & 4: x.isVegan()
         *
         * => !filter.isVegan() || x.isVegan()
         */
        return foods.stream()
            .filter{ x -> !filters.vegan || x.vegan }
            .filter{ x -> !filters.vegetarian || x.vegetarian }
            .filter{ x -> !filters.healthy || x.healthy }
            .filter{ x -> !filters.ecoFriendly || x.ecoFriendly }
            .filter{ x -> filters.calories != FilterState.ThreeStateFilterState.HIGH || x.highCalories }
            .filter{ x -> filters.calories != FilterState.ThreeStateFilterState.LOW || x.lowCalories }
            .filter{ x -> !filters.highProtein || x.highProtein }
            .filter{ x -> !filters.lowFat || x.lowFat }
            .filter{ x -> filters.carbs != FilterState.ThreeStateFilterState.HIGH || x.highCarbs }
            .filter{ x -> filters.carbs != FilterState.ThreeStateFilterState.LOW || x.lowCarbs }
            .filter{ x -> !filters.costEfficient || x.costEffective }
            .collect(Collectors.toList())
    }

    fun search(query: String, filters: FilterState): List<AFood> {
        // If the query is empty we only apply filter and do not search
        if (query.isEmpty()) {
            return applyFilter(testFoodSet, filters)
        }

        // Search using fuzzy search
        val matches: List<BoundExtractedResult<AFood>> = FuzzySearch.extractAll(query, testFoodSet, AFood::searchString)
        val sortedMatchesDescending: List<BoundExtractedResult<AFood>> = matches.stream().sorted(Collections.reverseOrder()).collect(Collectors.toList())

        if (sortedMatchesDescending.isEmpty()) {
            return emptyList()
        }

        // Get max score
        val maxScore: Int = sortedMatchesDescending[0].score

        // Return results
        return applyFilter(
            sortedMatchesDescending.stream()
                    .filter{x->x.score >= maxScore * 0.3} // Only get top 70% of results
                    .map(BoundExtractedResult<AFood>::getReferent) // Get the actual object
                    .collect(Collectors.toList()),
            filters
        )
    }

    fun getFoodById(id: String): AFood? {
        return testFoodSet.find { it.id == id }
    }
}