package com.littlelemon.littlelemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.littlelemon.littlelemon.ui.theme.LittleLemonTheme
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import io.ktor.client.statement.bodyAsText



class MainActivity : ComponentActivity() {

    private suspend fun fetchMenu(): List<MenuItemNetwork> {
        val url = "https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json"

        // Fetch raw response as a string
        val jsonString = client.get(url).bodyAsText()
        // Decode manually using Kotlinx Serilisation
        val response = Json { ignoreUnknownKeys= true }.decodeFromString<MenuNetworkdata>(jsonString)
        return response.menu
    }

    private fun saveMenuToDatabase(menuItemsNetwork: List<MenuItemNetwork>) {
        val menuItemsRoom = menuItemsNetwork.map { it.toMenuItemRoom() }
        database.menuDao().insertAll(menuItemsRoom)
    }

    private fun insertFallbackMenu() {
        val fallbackItems = listOf(
            MenuItemEntity(1, "Greek Salad", 10, "https://github.com/Meta-Mobile-Developer-PC/Working-With-Data-API/blob/main/images/greekSalad.jpg?raw=true", "starters", "The famous Greek salad of crispy lettuce, peppers, olives and our Chicago..."),
            MenuItemEntity(2, "Bruschetta", 8, "https://github.com/Meta-Mobile-Developer-PC/Working-With-Data-API/blob/main/images/bruschetta.jpg?raw=true", "starters", "Our Bruschetta is made from grilled bread that has been rubbed with garlic and seasoned with salt and olive oil."),
            MenuItemEntity(3, "Grilled Fish", 15, "https://github.com/Meta-Mobile-Developer-PC/Working-With-Data-API/blob/main/images/grilledFish.jpg?raw=true", "mains", "Subtle spice, rich and hearty flavor, this grilled fish is a Mediterranean delight."),
            MenuItemEntity(4, "Pasta", 12, "https://github.com/Meta-Mobile-Developer-PC/Working-With-Data-API/blob/main/images/pasta.jpg?raw=true", "mains", "Delicious homemade pasta served with rich tomato sauce."),
            MenuItemEntity(5, "Lemon Dessert", 5, "https://github.com/Meta-Mobile-Developer-PC/Working-With-Data-API/blob/main/images/lemonDessert.jpg?raw=true", "desserts", "Derived from grandma's secret recipe, this authentic lemon dessert is pure bliss.")
        )
        database.menuDao().insertAll(fallbackItems)
    }

    private val database by lazy {
        AppDatabase.getInstance(applicationContext)
    }

    // Create and configure the HTTP client instance
    private val client = HttpClient(Android)





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Triggers the fetch
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                if (database.menuDao().count() == 0) {
                    try {
                        val menuItemNetwork = fetchMenu()
                        saveMenuToDatabase(menuItemNetwork)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        insertFallbackMenu()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        setContent {
            LittleLemonTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        val navController = rememberNavController()
                        Box(modifier = Modifier.padding()) {
                            Navigation(navController = navController)
                        }
                    }
                }
            }
        }
    }
}



