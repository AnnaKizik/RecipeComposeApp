package com.yourcompany.recipecomposeapp.ui.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yourcompany.recipecomposeapp.core.ui.ImageResource
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
    Scaffold(
        modifier = modifier,
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier.padding(paddingValues)
            ) {
                item {
                    ScreenHeader(
                        screenCover = ImageResource.ResourceUrl(recipe.imageUrl),
                        screenTitle = recipe.title
                    )
                }
                item {
                    Text(
                        text = "Ингредиенты".uppercase(),
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.displayLarge
                    )
                }
                items(
                    recipe.ingredients, { it.name }
                ) { ingredient ->
                    Surface(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        shape = when {
                            ingredient == recipe.ingredients.first() -> RoundedCornerShape(
                                topStart = 8.dp,
                                topEnd = 8.dp
                            )

                            ingredient == recipe.ingredients.last() -> RoundedCornerShape(
                                bottomStart = 8.dp,
                                bottomEnd = 8.dp
                            )

                            else -> RectangleShape
                        },
                        color = MaterialTheme.colorScheme.surface,
                    ) {
                        Column {
                            Row(
                                modifier = Modifier
                                    .padding(12.dp)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = ingredient.name.uppercase(),
                                    textAlign = TextAlign.Start,
                                    color = MaterialTheme.colorScheme.onSecondary,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = ingredient.amount.uppercase(),
                                    textAlign = TextAlign.End,
                                    color = MaterialTheme.colorScheme.onSecondary,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                            if (ingredient != recipe.ingredients.last()) {
                                HorizontalDivider(
                                    modifier = Modifier
                                        .padding(
                                            horizontal = 12.dp,
                                        )
                                        .padding(top = 8.dp),
                                    color = MaterialTheme.colorScheme.outline,
                                    thickness = 1.dp,
                                )
                            }
                        }
                    }
                }
                item {
                    Text(
                        text = "Способ приготовления".uppercase(),
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.displayLarge
                    )
                }
                items(recipe.method) { step ->
                    Surface(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        shape = when {
                            step == recipe.method.first() -> RoundedCornerShape(
                                topStart = 8.dp,
                                topEnd = 8.dp
                            )

                            step == recipe.method.last() -> RoundedCornerShape(
                                bottomStart = 8.dp,
                                bottomEnd = 8.dp
                            )

                            else -> RectangleShape
                        },
                        color = MaterialTheme.colorScheme.surface,
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(12.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = step,
                                color = MaterialTheme.colorScheme.onSecondary,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            if (step != recipe.method.last()) {
                                HorizontalDivider(
                                    modifier = Modifier
                                        .padding(
                                            horizontal = 12.dp
                                        )
                                        .padding(top = 8.dp),
                                    color = MaterialTheme.colorScheme.outline,
                                    thickness = 1.dp,
                                )
                            }
                        }
                    }
                }
            }
        })
}

@Preview(showBackground = true)
@Composable
fun RecipeDetailScreenPreview() {
    RecipeComposeAppTheme {
        RecipeDetailsScreen(RecipesRepositoryStub.recipes[0].toUiModel())
    }
}