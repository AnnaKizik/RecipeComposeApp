package com.yourcompany.recipecomposeapp.features.recipes.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yourcompany.recipecomposeapp.data.repository.RecipesRepository
import com.yourcompany.recipecomposeapp.features.recipes.presentation.model.RecipesUiState
import com.yourcompany.recipecomposeapp.features.recipes.presentation.model.toUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipesViewModel(
    savedStateHandle: SavedStateHandle,
    private val recipesRepository: RecipesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RecipesUiState())
    val uiState: StateFlow<RecipesUiState> = _uiState.asStateFlow()

    private val categoryId: Int = savedStateHandle["categoryId"] ?: 0
    private val categoryTitle: String = savedStateHandle["categoryTitle"] ?: ""
    private val categoryImageUrl: String = savedStateHandle["categoryImageUrl"] ?: ""

    init {
        viewModelScope.launch {
            try {
                _uiState.update { currentState ->
                    currentState.copy(
                        recipesList = recipesRepository.getRecipesByCategory(categoryId)
                            .map { it.toUiModel() },
                        categoryTitle = categoryTitle,
                        categoryImgUrl = categoryImageUrl,
                        isLoading = false,
                        error = null,
                    )
                }
            } catch (e: Exception) {
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        error = "Ошибка при загрузке списка рецептов: $e"
                    )
                }
            }
        }
    }
}