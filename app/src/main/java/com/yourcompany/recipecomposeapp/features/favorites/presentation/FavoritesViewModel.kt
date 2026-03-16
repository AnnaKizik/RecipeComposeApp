package com.yourcompany.recipecomposeapp.features.favorites.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.yourcompany.recipecomposeapp.core.utils.FavoriteDataStoreManager
import com.yourcompany.recipecomposeapp.data.repository.RecipesRepositoryStub
import com.yourcompany.recipecomposeapp.features.favorites.presentation.model.FavoritesUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.collections.mapNotNull

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {

    private val favoriteManager = FavoriteDataStoreManager(application)
    private val recipesRepository = RecipesRepositoryStub

    private val _uiState = MutableStateFlow(FavoritesUiState())
    val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()

    init {
        favoriteManager.getFavoriteIdsFlow()
            .onEach { ids ->
                loadFavoritesList(ids)
            }
            .catch { e ->
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        error = "Возникла ошибка при обновлении избранного: $e"
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun loadFavoritesList(favoriteIds: Set<String>) {
        viewModelScope.launch {
            try {
                val favoritesList = favoriteIds.mapNotNull { id ->
                    val recipeId = id.toIntOrNull()
                    if (recipeId != null) recipesRepository.getRecipeDataByRecipeId(recipeId)
                    else null
                }
                _uiState.update { state ->
                    state.copy(
                        favoritesList = favoritesList,
                        isLoading = false,
                    )
                }
            } catch (e: Exception) {
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        error = "Ошибка при загрузке избранных рецептов: $e"
                    )
                }
            }
        }
    }
}