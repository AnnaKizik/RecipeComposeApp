package com.yourcompany.recipecomposeapp.ui.details

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.yourcompany.recipecomposeapp.core.ui.ImageResource
import com.yourcompany.recipecomposeapp.core.ui.ScreenHeader
import com.yourcompany.recipecomposeapp.data.repository.RecipesRepositoryStub
import com.yourcompany.recipecomposeapp.ui.recipes.model.RecipeUiModel
import com.yourcompany.recipecomposeapp.ui.recipes.model.toUiModel
import com.yourcompany.recipecomposeapp.ui.theme.RecipeComposeAppTheme
import kotlin.math.roundToInt

@Composable
fun RecipeDetailsScreen(
    recipe: RecipeUiModel,
    modifier: Modifier = Modifier,
) {
    var currentPortions by remember { mutableStateOf(recipe.servings) }

    val scaledIngredients = remember(currentPortions) {
        val multiplier = currentPortions.toDouble() / recipe.servings
        recipe.ingredients.map { ingredient ->
            if (ingredient.unitOfMeasure.isEmpty()) {
                ingredient.copy(quantity = ingredient.quantity)
            } else {
                ingredient.copy(
                    quantity = (ingredient.quantity.toDouble() * multiplier).toString()
                )
            }
        }
    }

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
                    PortionsSlider(
                        currentPortions = currentPortions,
                        onPortionsChange = { newPortionsCount ->
                            currentPortions = newPortionsCount
                        }
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
                    items = scaledIngredients,
                    key = { it.name }) { ingredient ->
                    IngredientItem(
                        ingredient = ingredient,
                        isFirst = ingredient == scaledIngredients.first(),
                        isLast = ingredient == scaledIngredients.last()
                    )
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PortionsSlider(
    currentPortions: Int,
    modifier: Modifier = Modifier,
    onPortionsChange: (Int) -> Unit,
) {
    Slider(
        modifier = modifier.padding(horizontal = 16.dp),
        value = currentPortions.toFloat(),
        onValueChange = { onPortionsChange(it.roundToInt()) },
        valueRange = 1f..12f,
        colors = SliderDefaults.colors(
            thumbColor = MaterialTheme.colorScheme.tertiary,
            activeTrackColor = MaterialTheme.colorScheme.tertiaryContainer,
            inactiveTrackColor = MaterialTheme.colorScheme.tertiaryContainer,
        ),
        thumb = {
            SliderDefaults.Thumb(
                interactionSource = remember { MutableInteractionSource() },
                thumbSize = DpSize(8.dp, 30.dp)
            )
        },
        enabled = true
    )
}

@Preview(showBackground = true)
@Composable
fun RecipeDetailScreenPreview() {
    RecipeComposeAppTheme {
        RecipeDetailsScreen(RecipesRepositoryStub.recipes[0].toUiModel())
    }
}