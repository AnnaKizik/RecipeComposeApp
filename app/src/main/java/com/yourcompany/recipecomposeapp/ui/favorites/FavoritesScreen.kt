package com.yourcompany.recipecomposeapp.ui.favorites

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yourcompany.recipecomposeapp.R
import com.yourcompany.recipecomposeapp.core.ui.ScreenHeader
import com.yourcompany.recipecomposeapp.ui.theme.RecipeComposeAppTheme

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    isFavoritesListEmpty: Boolean
) {
    Scaffold(
        modifier = modifier,
        content = { paddingValues ->
            Column(modifier = modifier.padding(paddingValues)) {
                ScreenHeader(
                    screenTitle = "ИЗБРАННОЕ",
                    screenCover = R.drawable.bcg_favorites
                )
                FavoritesList(
                    isEmpty = isFavoritesListEmpty,
                    emptyText = "Вы еще не добавили ни одного рецепта в избранное!"
                )
            }
        }
    )
}

@Composable
fun FavoritesList(
    modifier: Modifier = Modifier,
    isEmpty: Boolean,
    emptyText: String = "",
) {
    if (isEmpty) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = modifier,
                text = emptyText,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.labelLarge,
                textAlign = TextAlign.Center,
            )
        }
    } else {
        LazyColumn(
            modifier = modifier
                .fillMaxSize(),
        ) {
            items(6) {
                Surface(
                    modifier = modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.surface,
                ) {
                    Column {
                        Image(
                            modifier = modifier
                                .fillMaxSize()
                                .height(100.dp),
                            painter = painterResource(R.drawable.stub),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                        Text(
                            text = "НАЗВАНИЕ РЕЦЕПТА",
                            modifier = modifier.padding(8.dp),
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun FavoritesScreenPreview() {
    RecipeComposeAppTheme {
        FavoritesScreen(isFavoritesListEmpty = false)
    }
}

@Preview(showBackground = true)
@Composable
fun EmptyFavoritesScreenPreview() {
    RecipeComposeAppTheme {
        FavoritesScreen(isFavoritesListEmpty = true)
    }
}