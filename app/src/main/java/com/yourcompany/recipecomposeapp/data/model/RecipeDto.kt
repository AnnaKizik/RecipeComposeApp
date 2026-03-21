package com.yourcompany.recipecomposeapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecipeDto(
    @SerialName("id") val recipeId: Int,
    val categoryId: Int,
    val title: String,
    val ingredients: List<IngredientDto>,
    val method: List<String>,
    val imageUrl: String,
    val isFavorite: Boolean,
)