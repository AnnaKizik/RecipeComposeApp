package com.yourcompany.recipecomposeapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.yourcompany.recipecomposeapp.R
import com.yourcompany.recipecomposeapp.ui.theme.RecipeComposeAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenHeader(
    screenTitle: String,
    screenCover: Int
) {
    Box(
        modifier = Modifier
            .height(224.dp)
            .fillMaxWidth(),
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize(),
            painter = painterResource(screenCover),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
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

@Preview(showBackground = true)
@Composable
fun ScreenHeaderPreview() {
    RecipeComposeAppTheme {
        ScreenHeader(
            "ЗАГОЛОВОК",
            R.drawable.bcg_categories
        )
    }
}