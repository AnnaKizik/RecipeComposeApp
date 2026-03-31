package com.yourcompany.recipecomposeapp.features.details.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.yourcompany.recipecomposeapp.core.utils.FavoriteDataStoreManager
import com.yourcompany.recipecomposeapp.data.repository.RecipesRepository
import com.yourcompany.recipecomposeapp.features.details.presentation.model.RecipeDetailsUiState
import com.yourcompany.recipecomposeapp.features.recipes.presentation.model.toUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeDetailsViewModel(
    application: Application,
    savedStateHandle: SavedStateHandle,
    private val repository: RecipesRepository,
) :
    AndroidViewModel(application) {

    private val favoriteManager = FavoriteDataStoreManager(application)

    private val _uiState = MutableStateFlow(RecipeDetailsUiState())
    val uiState: StateFlow<RecipeDetailsUiState> = _uiState.asStateFlow()

    private val recipeId: Int = savedStateHandle["recipeId"] ?: 0

    init {
        loadRecipe(recipeId)
        subscribeToFavorites()
    }

    private fun subscribeToFavorites() {
        favoriteManager.getFavoriteIdsFlow().combine(_uiState) { ids, state ->
            state.copy(isFavorite = ids.contains(state.id.toString()))
        }
            .onEach { _uiState.value = it }
            .launchIn(viewModelScope)
    }

    private fun loadRecipe(recipeId: Int) {
        viewModelScope.launch {
            try {
                _uiState.update { state ->
                    val recipe = repository.getRecipe(recipeId).toUiModel()
                    state.copy(
                        id = recipe.id,
                        title = recipe.title,
                        imageUrl = recipe.imageUrl,
                        ingredients = recipe.ingredients,
                        method = recipe.method,
                        isLoading = false,
                    )
                }
            } catch (e: Exception) {
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        error = "Ошибка при загрузке рецепта: $e"
                    )
                }
            }
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            val isFavorite = _uiState.value.isFavorite
            if (isFavorite) favoriteManager.removeFavorite(_uiState.value.id)
            else favoriteManager.addFavorite(_uiState.value.id)
            _uiState.update { state ->
                state.copy(isFavorite = favoriteManager.isFavorite(state.id))
            }
        }
    }

    fun updatePortions(count: Int) {
        _uiState.update { state ->
            state.copy(portionsCount = count)
        }
    }
}