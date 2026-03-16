package com.yourcompany.recipecomposeapp.features.recipes.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yourcompany.recipecomposeapp.data.repository.RecipesRepositoryStub
import com.yourcompany.recipecomposeapp.features.recipes.presentation.model.RecipesUiState
import com.yourcompany.recipecomposeapp.features.recipes.presentation.model.toUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipesViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

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
                        recipesList = RecipesRepositoryStub.getRecipesByCategoryId(categoryId)
                            .map { it.toUiModel() },
                        categoryTitle = categoryTitle,
                        categoryImgUrl = categoryImageUrl,
                        isLoading = true,
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