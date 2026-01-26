package com.yourcompany.recipecomposeapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.yourcompany.recipecomposeapp.ui.categories.CategoriesScreen
import com.yourcompany.recipecomposeapp.ui.favorites.FavoritesScreen
import com.yourcompany.recipecomposeapp.ui.recipes.RecipesScreen
import com.yourcompany.recipecomposeapp.ui.theme.RecipeComposeAppTheme

@Composable
fun RecipesApp() {
    var currentScreen by remember { mutableStateOf(ScreenId.CATEGORIES) }
    var selectedCategoryId by remember { mutableStateOf<Int?>(null) }
    var selectedCategoryTitle by remember { mutableStateOf<String?>("Title") }

    val navigateToCategories = { currentScreen = ScreenId.CATEGORIES }
    val navigateToFavorites = { currentScreen = ScreenId.FAVORITES }

    RecipeComposeAppTheme {
        Scaffold(
            bottomBar = {
                BottomNavigation(
                    onCategoriesClick = navigateToCategories,
                    onFavoriteClick = navigateToFavorites,
                    currentScreen = currentScreen
                )
            },
            content = { paddingValues ->
                Box(
                    Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    when (currentScreen) {
                        ScreenId.CATEGORIES -> {
                            CategoriesScreen(
                                onCategoryClick = { categoryId, categoryTitle ->
                                    selectedCategoryId = categoryId
                                    selectedCategoryTitle = categoryTitle
                                    currentScreen = ScreenId.RECIPES
                                }
                            )
                        }

                        ScreenId.FAVORITES -> {
                            FavoritesScreen(isFavoritesListEmpty = false)
                        }

                        ScreenId.RECIPES -> {
                            if (selectedCategoryId != null) {
                                RecipesScreen(
                                    categoryId = selectedCategoryId,
                                    categoryTitle = selectedCategoryTitle ?: "Категория",
                                    onRecipeClick = {}
                                )
                            }
                        }
                    }
                }
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RecipesAppPreview() {
    RecipesApp()
}