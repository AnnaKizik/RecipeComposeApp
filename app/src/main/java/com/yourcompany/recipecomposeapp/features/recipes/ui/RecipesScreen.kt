package com.yourcompany.recipecomposeapp.features.recipes.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.yourcompany.recipecomposeapp.features.recipes.presentation.model.RecipeUiModel
import com.yourcompany.recipecomposeapp.core.ui.theme.RecipeComposeAppTheme
import com.yourcompany.recipecomposeapp.features.recipes.presentation.RecipesViewModel

@Composable
fun RecipesScreen(
    modifier: Modifier = Modifier,
    onRecipeClick: (Int, RecipeUiModel) -> Unit,
) {
    val recipesViewModel: RecipesViewModel = viewModel()
    val uiState by recipesViewModel.uiState.collectAsState()

    Scaffold(
        modifier = modifier,
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
            ) {
                ScreenHeader(
                    screenTitle = if (uiState.error != null) "КАТЕГОРИЯ" else uiState.categoryTitle.uppercase(),
                    screenCover = if (uiState.error != null) ImageResource.ResourceId(R.drawable.stub) else ImageResource.ResourceUrl(
                        uiState.categoryImgUrl
                    )
                )
                if (uiState.error != null || uiState.recipesListEmpty)
                    Text(
                        text = uiState.error ?: "Возникла ошибка при загрузке рецептов",
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.labelLarge,
                        textAlign = TextAlign.Center,
                    )
                else
                    LazyColumn {
                        items(uiState.recipesList, key = { it.id }) { recipe ->
                            RecipeItem(
                                recipe = recipe,
                                onRecipeClick = { onRecipeClick(recipe.id, recipe) },
                                modifier = Modifier
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                            )
                        }
                    }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun RecipesScreenPreview() {
    RecipeComposeAppTheme {
        RecipesScreen(
            onRecipeClick = { _, _ -> },
        )
    }
}