package com.yourcompany.recipecomposeapp.util

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.yourcompany.recipecomposeapp.PreferencesKeys
import com.yourcompany.recipecomposeapp.dataStore
import kotlinx.coroutines.flow.first

class FavoriteDataStoreManager(
    context: Context
) {
    private val preferences = context.dataStore

    suspend fun isFavorite(recipeId: Int): Boolean {
        val preferences = preferences.data.first()
        val favoritesIds = preferences[PreferencesKeys.FAVORITE_RECIPE_IDS] ?: emptySet()
        return favoritesIds.contains(recipeId.toString())
    }

    suspend fun addFavorite(recipeId: Int) {
        preferences.edit { preferences ->
            val currentFavorites = preferences[PreferencesKeys.FAVORITE_RECIPE_IDS]
            val updatedFavorites = currentFavorites?.plus(recipeId.toString())
            preferences[PreferencesKeys.FAVORITE_RECIPE_IDS] = updatedFavorites ?: emptySet()
        }
    }

    suspend fun removeFavorite(recipeId: Int) {
        preferences.edit { preferences ->
            val currentFavorites = preferences[PreferencesKeys.FAVORITE_RECIPE_IDS]
            val updatedFavorites = currentFavorites?.minus(recipeId.toString())
            preferences[PreferencesKeys.FAVORITE_RECIPE_IDS] = updatedFavorites ?: emptySet()
        }
    }

}