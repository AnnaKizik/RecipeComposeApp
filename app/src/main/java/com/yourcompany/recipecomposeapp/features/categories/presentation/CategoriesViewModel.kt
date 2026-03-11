package com.yourcompany.recipecomposeapp.features.categories.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yourcompany.recipecomposeapp.data.repository.RecipesRepositoryStub
import com.yourcompany.recipecomposeapp.features.categories.presentation.model.CategoriesUiState
import com.yourcompany.recipecomposeapp.features.categories.presentation.model.toUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoriesViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CategoriesUiState())
    val uiState: StateFlow<CategoriesUiState> = _uiState.asStateFlow()

    init {
        loadCategories()
    }

    fun loadCategories() {
        viewModelScope.launch {
            try {
                _uiState.update { currentState ->
                    currentState.copy(
                        categories = RecipesRepositoryStub.takeCategories().map { it.toUiModel() },
                        isLoading = true,
                    )
                }
            } catch (e: Exception) {
                _uiState.update { currentState ->
                    currentState.copy(
                        error = "Возникла ошибка при загрузке категорий: $e"
                    )
                }
            }
        }
    }
}