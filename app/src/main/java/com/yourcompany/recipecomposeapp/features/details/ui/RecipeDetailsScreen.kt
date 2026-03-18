package com.yourcompany.recipecomposeapp.features.details.ui

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yourcompany.recipecomposeapp.R
import com.yourcompany.recipecomposeapp.core.ui.ImageResource
import com.yourcompany.recipecomposeapp.core.ui.ScreenHeader
import com.yourcompany.recipecomposeapp.core.utils.shareRecipe
import com.yourcompany.recipecomposeapp.core.ui.theme.RecipeComposeAppTheme
import com.yourcompany.recipecomposeapp.features.details.presentation.RecipeDetailsViewModel
import kotlin.math.roundToInt

@Composable
fun RecipeDetailsScreen(
    modifier: Modifier = Modifier
) {
    val recipeDetailsViewModel: RecipeDetailsViewModel = viewModel()
    val uiState by recipeDetailsViewModel.uiState.collectAsState()

    val context = LocalContext.current

    Scaffold(
        modifier = modifier,
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier.padding(paddingValues)
            ) {
                item {
                    RecipeHeader(
                        recipeTitle = uiState.title,
                        recipeCoverUrl = uiState.imageUrl,
                        isFavorite = uiState.isFavorite,
                        onToggleFavorite = { recipeDetailsViewModel.toggleFavorite() },
                        onShareClick = {
                            shareRecipe(context, uiState.id, uiState.title)
                        },
                    )
                }
                item {
                    PortionsSlider(
                        currentPortions = uiState.portionsCount,
                        onPortionsChange = { newPortionsCount ->
                            recipeDetailsViewModel.updatePortions(newPortionsCount)
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
                    items = uiState.scaledIngredients,
                    key = { it.name }) { ingredient ->
                    IngredientItem(
                        ingredient = ingredient,
                        isFirst = ingredient == uiState.scaledIngredients.first(),
                        isLast = ingredient == uiState.scaledIngredients.last()
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
                items(uiState.method) { step ->
                    Surface(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        shape = when (step) {
                            uiState.method.first() -> RoundedCornerShape(
                                topStart = 8.dp,
                                topEnd = 8.dp
                            )

                            uiState.method.last() -> RoundedCornerShape(
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
                            if (step != uiState.method.last()) {
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

@Composable
fun RecipeHeader(
    recipeTitle: String,
    recipeCoverUrl: String,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    onShareClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        ScreenHeader(
            screenTitle = recipeTitle,
            screenCover = ImageResource.ResourceUrl(recipeCoverUrl)
        )
        Crossfade(
            modifier = Modifier
                .clickable(onClick = onToggleFavorite)
                .padding(top = 16.dp, end = 16.dp)
                .align(Alignment.TopEnd),
            targetState = isFavorite,
            animationSpec = tween(durationMillis = 300),
            label = "favorite_animation"
        ) { isCurrentFavorite ->
            val heartIcon = rememberVectorPainter(
                image = ImageVector.vectorResource(
                    id = if (isCurrentFavorite) R.drawable.ic_heart
                    else R.drawable.ic_heart_empty_btn,
                )
            )
            Icon(
                painter = heartIcon,
                tint = Color.Unspecified,
                contentDescription = "Favorite"
            )
        }
        Text(
            modifier = Modifier
                .clickable(onClick = onShareClick)
                .align(Alignment.TopStart)
                .padding(start = 16.dp, top = 16.dp),
            text = "Поделиться",
            color = MaterialTheme.colorScheme.surface,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RecipeDetailScreenPreview() {
    RecipeComposeAppTheme {
        RecipeDetailsScreen()
    }
}