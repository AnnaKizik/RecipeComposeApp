package com.yourcompany.recipecomposeapp.ui.recipes.model

import androidx.compose.runtime.Immutable
import com.yourcompany.recipecomposeapp.Constants
import com.yourcompany.recipecomposeapp.data.model.RecipeDto

@Immutable
data class RecipeUiModel(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val ingredients: List<IngredientUiModel>,
    val method: List<String>,
    val isFavorite: Boolean,
)

fun RecipeDto.toUiModel() = RecipeUiModel(
    id = recipeId,
    title = title,
    imageUrl = if (imageUrl.startsWith("http")) imageUrl
    else Constants.ASSETS_URI_PREFIX + imageUrl,
    ingredients = ingredients.map{it.toUiModel()},
    method = method,
    isFavorite = isFavorite
)