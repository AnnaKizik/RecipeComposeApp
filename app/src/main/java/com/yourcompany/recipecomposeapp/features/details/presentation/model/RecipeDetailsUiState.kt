package com.yourcompany.recipecomposeapp.features.details.presentation.model

import com.yourcompany.recipecomposeapp.features.recipes.presentation.model.IngredientUiModel

data class RecipeDetailsUiState(
    val id: Int = 0,
    val title: String = "",
    val imageUrl: String = "",
    val ingredients: List<IngredientUiModel> = emptyList(),
    val method: List<String> = emptyList(),
    val isFavorite: Boolean = false,
    val portionsCount: Int = 1,
    val isLoading: Boolean = true,
    val error: String? = null
) {
    val scaledIngredients = ingredients.map { ingredient ->
        if (ingredient.unitOfMeasure.isEmpty()) {
            ingredient.copy(quantity = ingredient.quantity)
        } else {
            ingredient.copy(
                quantity = (ingredient.quantity.toDouble() * portionsCount.toDouble()).toString()
            )
        }
    }
}