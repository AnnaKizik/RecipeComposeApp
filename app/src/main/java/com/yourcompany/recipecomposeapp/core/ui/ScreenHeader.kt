package com.yourcompany.recipecomposeapp.core.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.yourcompany.recipecomposeapp.R
import com.yourcompany.recipecomposeapp.ui.theme.RecipeComposeAppTheme

@Composable
fun ScreenHeader(
    screenTitle: String,
    screenCover: ImageResource,
) {
    Box(
        modifier = Modifier
            .height(224.dp)
            .fillMaxWidth(),
    ) {
        when (screenCover) {
            is ImageResource.ResourceId ->
                Image(
                    modifier = Modifier
                        .fillMaxSize(),
                    painter = painterResource(screenCover.resourceId),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )

            is ImageResource.ResourceUrl ->
                AsyncImage(
                    model = screenCover.url,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
        }
        Surface(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomStart),
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.surface,

            ) {
            Text(
                text = screenTitle,
                modifier = Modifier.padding(10.dp),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.displayLarge
            )
        }
    }
}

sealed class ImageResource {
    data class ResourceId(val resourceId: Int) : ImageResource()
    data class ResourceUrl(val url: String) : ImageResource()
}

@Preview(showBackground = true)
@Composable
fun ScreenHeaderPreview() {
    RecipeComposeAppTheme {
        ScreenHeader(
            "ЗАГОЛОВОК",
            ImageResource.ResourceId(R.drawable.bcg_categories)
        )
    }
}