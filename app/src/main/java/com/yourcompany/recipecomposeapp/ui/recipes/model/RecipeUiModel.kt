package com.yourcompany.recipecomposeapp.ui.recipes.model

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.yourcompany.recipecomposeapp.Constants
import com.yourcompany.recipecomposeapp.data.model.RecipeDto
import kotlinx.parcelize.Parcelize

@Parcelize
@Immutable
data class RecipeUiModel(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val servings: Int,
    val ingredients: List<IngredientUiModel>,
    val method: List<String>,
    val isFavorite: Boolean,
) : Parcelable

fun RecipeDto.toUiModel() = RecipeUiModel(
    id = recipeId,
    title = title,
    imageUrl = if (imageUrl.startsWith("http")) imageUrl
    else Constants.ASSETS_URI_PREFIX + imageUrl,
    servings = 1,
    ingredients = ingredients.map { it.toUiModel() },
    method = method,
    isFavorite = isFavorite
)