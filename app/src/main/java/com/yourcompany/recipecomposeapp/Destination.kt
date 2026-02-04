package com.yourcompany.recipecomposeapp

sealed class Destination(val route: String) {
    object Categories : Destination("categories")
    object Recipes : Destination("recipes/{categoryId}") {
        fun createRoute(categoryId: Int) = "recipes/${categoryId}"
    }

    object Recipe : Destination("recipe/{recipeId}") {
        fun createRoute(recipeId: Int) = "recipe/${recipeId}"
        const val KEY_RECIPE_OBJECT = "recipeObject"
    }

    object Favorites : Destination("favorites")
}