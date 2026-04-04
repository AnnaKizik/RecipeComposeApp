package com.yourcompany.recipecomposeapp.core.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.yourcompany.recipecomposeapp.R

@Composable
fun RecipeImage(
    imageUrl: String,
    contentDescription: String,
    modifier: Modifier,
    contentScale: ContentScale
) {
    AsyncImage(
        modifier = modifier,
        model = remember {
            ImageRequest.Builder(
                context = LocalContext.current
            )
                .data(imageUrl)
                .crossfade(300)
                .build()
        },
        contentScale = contentScale,
        placeholder = painterResource(R.drawable.img_placeholder),
        error = painterResource(R.drawable.img_error),
        loading = {
            Box(
                modifier = Modifier,
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(40.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        contentDescription = contentDescription
    )
}