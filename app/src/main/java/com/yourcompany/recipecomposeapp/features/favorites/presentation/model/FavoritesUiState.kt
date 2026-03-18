package com.yourcompany.recipecomposeapp.features.favorites.presentation.model

import com.yourcompany.recipecomposeapp.features.recipes.presentation.model.RecipeUiModel

data class FavoritesUiState(
    val favoritesList: List<RecipeUiModel> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)