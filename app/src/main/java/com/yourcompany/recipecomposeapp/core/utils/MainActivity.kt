package com.yourcompany.recipecomposeapp.core.utils

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.yourcompany.recipecomposeapp.core.ui.theme.RecipeComposeAppTheme
import com.yourcompany.recipecomposeapp.data.model.CategoryDto
import com.yourcompany.recipecomposeapp.data.model.RecipeDto
import kotlinx.serialization.json.Json
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : ComponentActivity() {
    private var deepLinkIntent by mutableStateOf<Intent?>(null)

    private val threadPool: ExecutorService = Executors.newFixedThreadPool(10)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i("pool", "Метод onCreate() выполняется на потоке: ${Thread.currentThread().name}")

        threadPool.execute {
            val url = URL("https://recipes.androidsprint.ru/api/category")
            val connection = url.openConnection() as HttpURLConnection

            try {
                connection.connect()

                Log.i("pool", "Выполняю запрос на потоке: ${Thread.currentThread().name}")

                Log.i("!!!", "responseCode: ${connection.responseCode}")
                Log.i("!!!", "responseMessage: ${connection.responseMessage}")

                val responseBody = connection.inputStream.bufferedReader().use { it.readText() }
                Log.i("!!!", "Body: $responseBody")

                val data = Json.decodeFromString<List<CategoryDto>>(responseBody)
                Log.i("!!!", "Количество категорий: ${data.size}")
                Log.i("!!!", "Названия категорий: ${data.map { it.title }}")

                threadPool.execute {
                    for (i in data.map { it.id }) {
                        val url = URL("https://recipes.androidsprint.ru/api/category/${i}/recipes")
                        val connection = url.openConnection() as HttpURLConnection

                        try {
                            connection.connect()

                            Log.i("pool", "Выполняю запрос на потоке: ${Thread.currentThread().name}")

                            val recipesResponseBody =
                                connection.inputStream.bufferedReader().use { it.readText() }
                            Log.i("!!!", "Body: $recipesResponseBody")
                            val recipesData =
                                Json.decodeFromString<List<RecipeDto>>(recipesResponseBody)
                            Log.i("!!!", "Количество рецептов: ${recipesData.size}")

                        } catch (e: Exception) {
                            throw IllegalThreadStateException("Ошибка при получении рецептов категории: $e")
                        } finally {
                            connection.disconnect()
                        }
                    }
                }

            } catch (e: Exception) {
                throw IllegalThreadStateException("Ошибка при получении категорий: $e")
            } finally {
                connection.disconnect()
            }
        }

        intent?.data?.let { _ ->
            deepLinkIntent = intent
        }

        enableEdgeToEdge()
        setContent {
            RecipesApp(deepLinkIntent = deepLinkIntent)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        intent.data?.let { _ ->
            deepLinkIntent = intent
        }
        setIntent(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        threadPool.shutdown()
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RecipeComposeAppTheme {
        Greeting("Android")
    }
}