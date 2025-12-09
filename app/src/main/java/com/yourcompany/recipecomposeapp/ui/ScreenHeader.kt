package com.yourcompany.recipecomposeapp.ui

import android.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.yourcompany.recipecomposeapp.ui.theme.RecipeComposeAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenHeader(
    screenTitle: String,
    screenCover: Int
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(screenCover),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
        Surface(
            modifier = Modifier,
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Text(
                screenTitle,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.displayLarge
            )
        }
    }
}