package com.yourcompany.recipecomposeapp.features.categories.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yourcompany.recipecomposeapp.R
import com.yourcompany.recipecomposeapp.core.ui.ImageResource
import com.yourcompany.recipecomposeapp.core.ui.ScreenHeader
import com.yourcompany.recipecomposeapp.core.ui.theme.RecipeComposeAppTheme
import com.yourcompany.recipecomposeapp.features.categories.presentation.CategoriesViewModel
import com.yourcompany.recipecomposeapp.features.categories.presentation.model.CategoryUiModel

@Composable
fun CategoriesScreen(
    onCategoryClick: (Int, String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: CategoriesViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = modifier,
        content = { paddingValues ->
            Column(modifier = modifier.padding(paddingValues)) {
                ScreenHeader(
                    "КАТЕГОРИИ",
                    ImageResource.ResourceId(R.drawable.bcg_categories)
                )
                if (uiState.error != null) {
                    Text(
                        text = uiState.error ?: "Возникла ошибка при загрузке категорий",
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.labelLarge,
                        textAlign = TextAlign.Center,
                    )
                } else {
                    CategoriesList(
                        categoriesList = uiState.categories,
                        onCategoryClick = { category ->
                            onCategoryClick(category.id, category.title, category.imageUrl)
                        }
                    )
                }
            }
        }
    )
}

@Composable
fun CategoriesList(
    categoriesList: List<CategoryUiModel>,
    onCategoryClick: (CategoryUiModel) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            categoriesList,
            key = { it.id }) { category ->
            CategoryItem(
                imageUrl = category.imageUrl,
                title = category.title,
                description = category.description,
                onCategoryClick = { onCategoryClick(category) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CategoriesScreenPreview() {
    RecipeComposeAppTheme {
        CategoriesScreen(
            onCategoryClick = { _: Int, _: String, _: String -> }
        )
    }
}