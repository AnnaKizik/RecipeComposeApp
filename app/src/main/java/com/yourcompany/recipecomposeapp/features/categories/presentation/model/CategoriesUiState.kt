package com.yourcompany.recipecomposeapp.features.categories.presentation.model

data class CategoriesUiState(
    val categories: List<CategoryUiModel> = emptyList(),
    val isLoaded: Boolean = false,
    val error: String? = null
)