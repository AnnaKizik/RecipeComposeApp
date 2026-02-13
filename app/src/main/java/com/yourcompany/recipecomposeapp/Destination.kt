package com.yourcompany.recipecomposeapp

sealed class Destination(val route: String) {
    object Categories : Destination("categories")
    object Recipes : Destination("recipes/{categoryId}") {
        fun createRoute(categoryId: Int) = "recipes/${categoryId}"
    }

    object Recipe : Destination("recipe/{recipeId}") {
        fun createRoute(recipeId: Int) = "recipe/${recipeId}"
        fun createRecipeDeepLink(recipeId: Int) = "$DEEP_LINK_BASE_URL/recipe/$recipeId"
        const val KEY_RECIPE_OBJECT = "recipeObject"
        const val DEEP_LINK_SCHEME = "recipeapp"
        const val DEEP_LINK_BASE_URL = "https://recipes.androidsprint.ru"
    }

    object Favorites : Destination("favorites")
}