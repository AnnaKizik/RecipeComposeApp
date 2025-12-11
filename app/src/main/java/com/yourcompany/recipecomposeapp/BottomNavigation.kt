package com.yourcompany.recipecomposeapp

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BottomNavigation(
    currentScreen: ScreenId,
    onCategoriesClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationBar(modifier = modifier) {
        NavigationBarItem(
            selected = currentScreen == ScreenId.CATEGORIES,
            onClick = onCategoriesClick,
            label = { Text("Категории") },
            icon = {},
        )

        NavigationBarItem(
            selected = currentScreen == ScreenId.FAVORITES,
            onClick = onFavoriteClick,
            icon = {},
            label = { Text("Избранное") }
        )
    }

}