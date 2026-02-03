package com.yourcompany.recipecomposeapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.yourcompany.recipecomposeapp.ui.theme.RecipeComposeAppTheme

@Composable
fun BottomNavigation(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStack?.destination?.route
    NavigationBar(
        modifier = modifier
            .padding(horizontal = 16.dp),
        containerColor = Color.Transparent
    ) {
        NavigationBarItem(
            selected = currentRoute == Destination.Categories.route,
            onClick = {
                if (currentRoute != Destination.Categories.route)
                    navController.navigate(Destination.Categories.route)
            },
            label = {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.tertiary,
                ) {
                    Text(
                        text = "КАТЕГОРИИ",
                        modifier = Modifier.padding(8.dp),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.surface,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            },
            icon = {},
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent
            )
        )
        NavigationBarItem(
            selected = currentRoute == Destination.Favorites.route,
            onClick = {
                if (currentRoute != Destination.Favorites.route)
                    navController.navigate(Destination.Favorites.route)
            },
            icon = {},
            label = {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.error,
                ) {
                    Row(
                        modifier = Modifier
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    )
                    {
                        Text(
                            text = "ИЗБРАННОЕ",
                            modifier = Modifier,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.surface,
                            style = MaterialTheme.typography.labelLarge
                        )
                        Image(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            painter = painterResource(R.drawable.ic_heart_empty),
                            contentDescription = null,
                            alignment = Alignment.CenterEnd
                        )
                    }
                }
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationPreview() {
    RecipeComposeAppTheme {
        val navController = rememberNavController()
        BottomNavigation(navController = navController)
    }
}