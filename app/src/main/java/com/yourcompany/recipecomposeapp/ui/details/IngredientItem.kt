package com.yourcompany.recipecomposeapp.ui.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.yourcompany.recipecomposeapp.ui.recipes.model.IngredientUiModel

@Composable
fun IngredientItem(
    modifier: Modifier = Modifier,
    ingredient: IngredientUiModel,
    isFirst: Boolean,
    isLast: Boolean,
) {
    Surface(
        modifier = modifier.padding(horizontal = 16.dp),
        shape = when {
            isFirst -> RoundedCornerShape(
                topStart = 8.dp,
                topEnd = 8.dp
            )

            isLast -> RoundedCornerShape(
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
                    text = "${ingredient.quantity} ${ingredient.unitOfMeasure}".uppercase(),
                    textAlign = TextAlign.End,
                    color = MaterialTheme.colorScheme.onSecondary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            if (!isLast) {
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