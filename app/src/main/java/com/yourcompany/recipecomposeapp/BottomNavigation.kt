package com.yourcompany.recipecomposeapp

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BottomNavigation(
    onCategoriesClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationBar(modifier = modifier) {
        NavigationBarItem(
            selected = false,
            onClick = onCategoriesClick,
            icon = {},
            label = { Text("Категории") },
        )

        NavigationBarItem(
            selected = false,
            onClick = onFavoriteClick,
            icon = {},
            label = { Text("Избранное") }
        )
    }

}