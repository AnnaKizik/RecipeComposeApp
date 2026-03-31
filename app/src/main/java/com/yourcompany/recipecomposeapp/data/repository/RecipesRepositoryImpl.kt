package com.yourcompany.recipecomposeapp.data.repository

import android.util.Log
import com.yourcompany.recipecomposeapp.core.network.api.RecipesApiService
import com.yourcompany.recipecomposeapp.data.model.CategoryDto
import com.yourcompany.recipecomposeapp.data.model.RecipeDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipesRepositoryImpl(
    private val apiService: RecipesApiService
) : RecipesRepository {
    override suspend fun getCategories(): List<CategoryDto> {
        return withContext(Dispatchers.IO) {
            try {
                apiService.getCategories()
            } catch (e: Exception) {
                Log.e("repository_error", "Ошибка загрузки категорий: $e")
                emptyList()
            }
        }
    }

    override suspend fun getRecipesByCategory(categoryId: Int): List<RecipeDto> {
        return withContext(Dispatchers.IO) {
            try {
                apiService.getRecipesByCategory(categoryId)
            } catch (e: Exception) {
                Log.e("repository_error", "Ошибка загрузки рецептов категории: $e")
                emptyList()
            }
        }
    }

    override suspend fun getRecipe(recipeId: Int): RecipeDto {
        return withContext(Dispatchers.IO) {
            try {
                apiService.getRecipe(recipeId)
            } catch (e: Exception) {
                Log.e("repository_error", "Ошибка загрузки рецепта: $e")
                throw IllegalStateException("Ошибка загрузки рецепта")
            }
        }
    }

}