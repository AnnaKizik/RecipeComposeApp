package com.yourcompany.recipecomposeapp.ui.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yourcompany.recipecomposeapp.R
import com.yourcompany.recipecomposeapp.core.ui.ScreenHeader
import com.yourcompany.recipecomposeapp.data.repository.RecipesRepositoryStub
import com.yourcompany.recipecomposeapp.ui.categories.model.toUiModel
import com.yourcompany.recipecomposeapp.ui.theme.RecipeComposeAppTheme

@Composable
fun CategoriesScreen(
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        content = { paddingValues ->
            Column(modifier = modifier.padding(paddingValues)) {
                ScreenHeader(
                    "КАТЕГОРИИ",
                    R.drawable.bcg_categories
                )
                CategoriesList(
                    onCategoryClick = { categoryId ->
                        RecipesRepositoryStub.getRecipesByCategoryId(categoryId)
                    }
                )
            }
        }
    )
}

@Composable
fun CategoriesList(
    modifier: Modifier = Modifier,
    onCategoryClick: (categoryId: Int) -> Unit,
) {
    var categoriesList by remember {
        mutableStateOf(RecipesRepositoryStub.takeCategories())
    }
    val categoriesUiModelList = categoriesList.map { it.toUiModel() }
    LazyVerticalGrid(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(categoriesUiModelList) { category ->
            CategoryItem(
                imageUrl = category.imageUrl,
                title = category.title,
                description = category.description
            ){}
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoriesScreenPreview() {
    RecipeComposeAppTheme {
        CategoriesScreen()
    }
}