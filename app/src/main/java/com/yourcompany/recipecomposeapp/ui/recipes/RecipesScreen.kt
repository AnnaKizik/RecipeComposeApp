package com.yourcompany.recipecomposeapp.ui.recipes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yourcompany.recipecomposeapp.R
import com.yourcompany.recipecomposeapp.core.ui.ImageResource
import com.yourcompany.recipecomposeapp.core.ui.ScreenHeader
import com.yourcompany.recipecomposeapp.data.repository.RecipesRepositoryStub
import com.yourcompany.recipecomposeapp.data.repository.RecipesRepositoryStub.getRecipesByCategoryId
import com.yourcompany.recipecomposeapp.ui.categories.model.toUiModel
import com.yourcompany.recipecomposeapp.ui.recipes.model.RecipeUiModel
import com.yourcompany.recipecomposeapp.ui.recipes.model.toUiModel
import com.yourcompany.recipecomposeapp.ui.theme.RecipeComposeAppTheme

@Composable
fun RecipesScreen(
    modifier: Modifier = Modifier,
    categoryId: Int?,
    onRecipeClick: (Int, RecipeUiModel) -> Unit,
) {
    var recipes by remember { mutableStateOf<List<RecipeUiModel>>(emptyList()) }
    val category = RecipesRepositoryStub.categories.find { it.id == categoryId }?.toUiModel()

    LaunchedEffect(categoryId) {
        categoryId?.let {
            recipes = getRecipesByCategoryId(it).map { dto -> dto.toUiModel() }
        }
    }

    Scaffold(
        modifier = modifier,
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
            ) {
                ScreenHeader(
                    screenTitle = category?.title?.uppercase() ?: "Категория",
                    screenCover = if (category?.imageUrl != null) ImageResource.ResourceUrl(category.imageUrl)
                    else ImageResource.ResourceId(R.drawable.stub)
                )
                LazyColumn {
                    items(recipes, key = { it.id }) { recipe ->
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
            categoryId = 0,
            onRecipeClick = { _, _ -> }
        )
    }
}