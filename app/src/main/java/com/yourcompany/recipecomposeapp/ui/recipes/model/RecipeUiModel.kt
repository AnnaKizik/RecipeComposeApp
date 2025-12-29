package com.yourcompany.recipecomposeapp.ui.recipes.model

import androidx.compose.runtime.Immutable
import com.yourcompany.recipecomposeapp.Constants
import com.yourcompany.recipecomposeapp.data.model.IngredientDto
import com.yourcompany.recipecomposeapp.data.model.RecipeDto

@Immutable
data class RecipeUiModel(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val ingredients: List<IngredientDto>,
    val method: List<String>,
    val isFavorite: Boolean,
)

fun RecipeDto.toUiModel() = RecipeUiModel(
    id = id,
    title = title,
    imageUrl = if (imageUrl.startsWith("http")) imageUrl
    else Constants.ASSETS_URI_PREFIX + imageUrl,
    ingredients = ingredients,
    method = method,
    isFavorite = isFavorite
)