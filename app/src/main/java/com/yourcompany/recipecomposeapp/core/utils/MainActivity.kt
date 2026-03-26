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
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : ComponentActivity() {
    private var deepLinkIntent by mutableStateOf<Intent?>(null)

    private val threadPool: ExecutorService = Executors.newFixedThreadPool(10)
    private val okHttpClient = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i("pool", "Метод onCreate() выполняется на потоке: ${Thread.currentThread().name}")

        threadPool.execute {

            val request = Request.Builder()
                .url("https://recipes.androidsprint.ru/api/category")
                .build()

            try {
                okHttpClient.newCall(request).execute().use { response ->
                    Log.i("pool", "Выполняю запрос на потоке: ${Thread.currentThread().name}")
                    Log.i("!!!", "responseCode: ${response.code}")
                    Log.i("!!!", "responseMessage: ${response.message}")
                    val categories =
                        Json.decodeFromString<List<CategoryDto>>(response.body.toString())
                    Log.i("!!!", "Полученные категории: ${categories.map { it.title }}")

                    for (i in categories.map { it.id }) {
                        threadPool.execute {

                            val recipesRequest = Request.Builder()
                                .url("https://recipes.androidsprint.ru/api/category/${i}/recipes")
                                .build()

                            try {
                                okHttpClient.newCall(recipesRequest).execute().use { response ->
                                    Log.i(
                                        "pool",
                                        "Выполняю запрос на потоке: ${Thread.currentThread().name}"
                                    )
                                    val recipesData =
                                        Json.decodeFromString<List<RecipeDto>>(response.body.toString())
                                    Log.i("!!!", "Количество рецептов: ${recipesData.size}")
                                }
                            } catch (e: Exception) {
                                Log.e("thread_error", "Ошибка при получении рецептов категории: $e")
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("thread_error", "Ошибка при получении категорий: $e")
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