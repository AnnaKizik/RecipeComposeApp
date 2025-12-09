package com.yourcompany.recipecomposeapp.ui.categories

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.yourcompany.recipecomposeapp.R
import com.yourcompany.recipecomposeapp.ui.ScreenHeader
import com.yourcompany.recipecomposeapp.ui.theme.RecipeComposeAppTheme

@Composable
fun CategoriesScreen() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = {
            Column {
                ScreenHeader(
                    "КАТЕГОРИИ",
                    R.drawable.bcg_categories
                )

                Text(
                    "Список категорий"

                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun CategoriesScreenPreview() {
    RecipeComposeAppTheme {
        CategoriesScreen()
    }
}