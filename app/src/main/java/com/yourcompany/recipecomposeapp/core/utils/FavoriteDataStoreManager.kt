package com.yourcompany.recipecomposeapp.core.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class FavoriteDataStoreManager(
    context: Context
) {
    private val preferences = context.dataStore
    private val preferencesFlow = context.dataStore.data

    suspend fun isFavorite(recipeId: Int): Boolean {
        val preferences = preferences.data.first()
        val favoritesIds = preferences[PreferencesKeys.FAVORITE_RECIPE_IDS] ?: emptySet()
        return favoritesIds.contains(recipeId.toString())
    }

    suspend fun addFavorite(recipeId: Int) {
        preferences.edit { preferences ->
            val currentFavorites = preferences[PreferencesKeys.FAVORITE_RECIPE_IDS] ?: emptySet()
            val updatedFavorites = currentFavorites.plus(recipeId.toString())
            preferences[PreferencesKeys.FAVORITE_RECIPE_IDS] = updatedFavorites
        }
    }

    suspend fun removeFavorite(recipeId: Int) {
        preferences.edit { preferences ->
            val currentFavorites = preferences[PreferencesKeys.FAVORITE_RECIPE_IDS] ?: emptySet()
            val updatedFavorites = currentFavorites.minus(recipeId.toString())
            preferences[PreferencesKeys.FAVORITE_RECIPE_IDS] = updatedFavorites
        }
    }

    fun getFavoriteIdsFlow(): Flow<Set<String>> = preferencesFlow.map { preferences ->
        preferences[PreferencesKeys.FAVORITE_RECIPE_IDS] ?: emptySet()
    }

    fun isFavoriteFlow(recipeId: Int): Flow<Boolean> = getFavoriteIdsFlow().map { ids ->
        ids.contains(recipeId.toString())
    }

    fun getFavoriteCountFlow(): Flow<Int> = getFavoriteIdsFlow().map { ids ->
        ids.size
    }

}