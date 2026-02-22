package com.yourcompany.recipecomposeapp.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class FavoritePrefsManager(
    context: Context
) {
    val sharedPreferences: SharedPreferences? =
        context.getSharedPreferences(FAVORITE_RECIPES_IDS, Context.MODE_PRIVATE)

    fun checkIsFavorite(recipeId: Int): Boolean {
        val allFavorites = getAllFavorites()
        return allFavorites.find { it == recipeId.toString() } != null
    }

    fun addToFavorites(recipeId: Int) {
        val currentFavorites = getAllFavorites()
        val updatedFavorites = currentFavorites.toMutableSet()
        updatedFavorites.add("$recipeId")

        sharedPreferences?.edit {
            putStringSet(FAVORITE_RECIPES_IDS, updatedFavorites)
        }
    }

    fun removeFromFavorite(recipeId: Int) {
        val currentFavorites = getAllFavorites()
        val updatedFavorites = currentFavorites.toMutableSet()
        updatedFavorites.remove("$recipeId")

        sharedPreferences?.edit {
            putStringSet(FAVORITE_RECIPES_IDS, updatedFavorites)
        }
    }

    fun getAllFavorites(): Set<String> {
        val favoriteRecipesIds =
            sharedPreferences?.getStringSet(FAVORITE_RECIPES_IDS, emptySet())?.toSet() ?: emptySet()
        return favoriteRecipesIds
    }

    companion object {
        const val FAVORITE_RECIPES_IDS = "favorite_recipes_ids"
    }
}
