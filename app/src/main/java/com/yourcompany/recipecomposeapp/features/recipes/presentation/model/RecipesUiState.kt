package com.yourcompany.recipecomposeapp.features.recipes.presentation.model

data class RecipesUiState(
    val recipesList: List<RecipeUiModel> = emptyList(),
    val categoryTitle: String = "",
    val categoryImgUrl: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
) {
    val recipesListEmpty = recipesList.isEmpty()
}