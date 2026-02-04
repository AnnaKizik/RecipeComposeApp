package com.yourcompany.recipecomposeapp.ui.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.yourcompany.recipecomposeapp.R
import com.yourcompany.recipecomposeapp.core.ui.ScreenHeader
import com.yourcompany.recipecomposeapp.data.repository.RecipesRepositoryStub
import com.yourcompany.recipecomposeapp.ui.recipes.model.RecipeUiModel
import com.yourcompany.recipecomposeapp.ui.recipes.model.toUiModel
import com.yourcompany.recipecomposeapp.ui.theme.RecipeComposeAppTheme

@Composable
fun RecipeDetailsScreen(
    recipe: RecipeUiModel,
    modifier: Modifier = Modifier,
) {
    val recipeCoverUrl = recipe.imageUrl
    Scaffold(
        modifier = modifier,
        content = { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues)
            ) {
                ScreenHeader(
                    screenCover = painterResource(recipeCoverUrl),
                    screenTitle = recipe.title
                )
            }
            LazyColumn() {
                item {
                    Text(text = "Ингредиенты".uppercase())
                }
                items(recipe.ingredients, { it.name }) { ingredient ->
                    Text(text = ingredient.name, textAlign = TextAlign.Start)
                    Text(text = ingredient.amount, textAlign = TextAlign.End)
                }
                item {
                    Text(text = "Способ приготовления".uppercase())
                }
                items(recipe.method) { step ->
                    Text(text = step)
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun RecipeDetailScreenPreview(){
    RecipeComposeAppTheme {
        RecipeDetailsScreen(RecipesRepositoryStub.recipes.get(0).toUiModel())
    }
}