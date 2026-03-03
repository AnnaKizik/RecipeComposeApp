package com.yourcompany.recipecomposeapp.features.favorites.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yourcompany.recipecomposeapp.R
import com.yourcompany.recipecomposeapp.core.ui.ImageResource
import com.yourcompany.recipecomposeapp.core.ui.ScreenHeader
import com.yourcompany.recipecomposeapp.data.repository.RecipesRepositoryStub
import com.yourcompany.recipecomposeapp.features.recipes.ui.RecipeItem
import com.yourcompany.recipecomposeapp.core.ui.theme.RecipeComposeAppTheme
import com.yourcompany.recipecomposeapp.core.utils.FavoriteDataStoreManager
import kotlinx.coroutines.flow.mapNotNull

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    recipesRepository: RecipesRepositoryStub = RecipesRepositoryStub,
    onRecipeClick: (Int) -> Unit,
) {
    val context = LocalContext.current
    val favoriteDataStoreManager = remember { FavoriteDataStoreManager(context) }

    val favoritesList by favoriteDataStoreManager
        .getFavoriteIdsFlow()
        .mapNotNull{ favoriteIds ->
            favoriteIds.mapNotNull { id ->
                val recipeId = id.toIntOrNull()
                if (recipeId != null) recipesRepository.getRecipeDataByRecipeId(recipeId)
                else null
            }
        }
        .collectAsState(
            initial = emptyList()
        )

Scaffold(
modifier = modifier,
content = {
    paddingValues ->
    Column(modifier = modifier.padding(paddingValues)) {
        ScreenHeader(
            screenTitle = "ИЗБРАННОЕ",
            screenCover = ImageResource.ResourceId(R.drawable.bcg_favorites)
        )
        if (favoritesList.isEmpty()) {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = modifier,
                    text = "Вы еще не добавили ни одного рецепта в избранное!",
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.labelLarge,
                    textAlign = TextAlign.Center,
                )
            }
        } else {
            LazyColumn {
                items(items = favoritesList, key = { it.id }) { recipe ->
                    RecipeItem(
                        recipe = recipe,
                        onRecipeClick = onRecipeClick,
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }
        }
    }
}
)
}

@Preview(showBackground = true)
@Composable
fun FavoritesScreenPreview() {
    RecipeComposeAppTheme {
        FavoritesScreen(
            onRecipeClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EmptyFavoritesScreenPreview() {
    RecipeComposeAppTheme {
        FavoritesScreen(
            onRecipeClick = {}
        )
    }
}