package com.yourcompany.recipecomposeapp.ui.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.yourcompany.recipecomposeapp.R
import com.yourcompany.recipecomposeapp.ui.theme.RecipeComposeAppTheme

@Composable
fun CategoryItem(
    modifier: Modifier = Modifier,
    imageUrl: String,
    title: String,
    description: String,
    onClick: () -> Unit,
) {
    Card(
        onClick = onClick,
        modifier = modifier,
    ) {
        Column(
            modifier = modifier.background(color = MaterialTheme.colorScheme.surface)
        ) {
            AsyncImage(
                modifier = modifier.height(130.dp),
                model = imageUrl,
                placeholder = painterResource(R.drawable.img_placeholder),
                error = painterResource(R.drawable.img_error),
                contentScale = ContentScale.Crop,
                contentDescription = "Изображение категории",

            )
            Text(
                text = title.uppercase(),
                modifier = modifier.padding(8.dp),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = description,
                modifier = modifier
                    .padding(horizontal = 8.dp)
                    .padding(bottom = 10.dp),
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryItemPreview() {
    RecipeComposeAppTheme {
        CategoryItem(
            imageUrl = "burger.png",
            title = "ЗАГОЛОВОК",
            description = "Описание категории",
            onClick = {},
        )
    }
}