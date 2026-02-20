package com.yourcompany.recipecomposeapp

import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.yourcompany.recipecomposeapp.data.repository.RecipesRepositoryStub
import com.yourcompany.recipecomposeapp.ui.categories.CategoriesScreen
import com.yourcompany.recipecomposeapp.ui.details.RecipeDetailsScreen
import com.yourcompany.recipecomposeapp.ui.favorites.FavoritesScreen
import com.yourcompany.recipecomposeapp.ui.recipes.RecipesScreen
import com.yourcompany.recipecomposeapp.ui.theme.RecipeComposeAppTheme
import com.yourcompany.recipecomposeapp.util.FavoritePrefsManager
import kotlinx.coroutines.delay

@Composable
fun RecipesApp(deepLinkIntent: Intent?) {
    val navController = rememberNavController()
    val context = LocalContext.current

    LaunchedEffect(deepLinkIntent) {
        deepLinkIntent?.data?.let { uri ->
            val recipeId: Int? = when (uri.scheme) {
                "recipeapp" ->
                    if (uri.host == "recipe") uri.pathSegments[0].toIntOrNull() else null

                "https", "http" ->
                    if (uri.pathSegments[0] == "recipe") uri.pathSegments[1].toIntOrNull() else null

                else -> null
            }

            if (recipeId != null) {
                delay(100)
                navController.navigate(Destination.Recipe.createRoute(recipeId))
            }
        }
    }

    RecipeComposeAppTheme {
        Scaffold(
            bottomBar = {
                BottomNavigation(
                    navController = navController
                )
            }
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = Destination.Categories.route,
                modifier = Modifier.padding(paddingValues)
            ) {
                composable(Destination.Categories.route) {
                    CategoriesScreen(
                        onCategoryClick = { categoryId ->
                            navController.navigate(Destination.Recipes.createRoute(categoryId))
                        }
                    )
                }
                composable(
                    route = Destination.Recipes.route,
                    arguments = listOf(navArgument("categoryId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val categoryId = backStackEntry.arguments?.getInt("categoryId") ?: 0
                    RecipesScreen(
                        categoryId = categoryId,
                        onRecipeClick = { recipeId, recipe ->
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                Destination.Recipe.KEY_RECIPE_OBJECT,
                                recipe
                            )
                            navController.navigate(Destination.Recipe.createRoute(recipeId))
                        }
                    )
                }
                composable(
                    route = Destination.Recipe.route,
                    arguments = listOf(navArgument("recipeId") {
                        type = NavType.IntType
                    })
                ) { backStackEntry ->
                    val recipeId = backStackEntry.arguments?.getInt("recipeId") ?: 0
                    val recipe = RecipesRepositoryStub.getRecipeDataByRecipeId(recipeId)
                    RecipeDetailsScreen(
                        recipe = recipe,
                        isFavorite = FavoritePrefsManager(context).checkIsFavorite(recipeId),
                        onToggleFavorite = {
                            if (recipe.isFavorite) {
                                FavoritePrefsManager(context).removeFromFavorite(recipe.id)
                            } else {
                                FavoritePrefsManager(context).addToFavorites(recipe.id)
                            }
                        },
                    )
                }
                composable(
                    route = Destination.Favorites.route,
                ) {
                    FavoritesScreen(
                        onRecipeClick = { recipeId ->
                            navController.navigate(Destination.Recipe.createRoute(recipeId))
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RecipesAppPreview() {
    RecipesApp(null)
}