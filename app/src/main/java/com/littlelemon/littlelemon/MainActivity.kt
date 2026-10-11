package com.littlelemon.littlelemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
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


class MainActivity : ComponentActivity() {

    private suspend fun fetchMenu(): List<MenuItemNetwork> {
        val url = "https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json"
        val response: MenuNetworkdata = client.get(url).body()
        return response.menu
    }

    private fun saveMenuToDatabase(menuItemsNetwork: List<MenuItemNetwork>) {
        val menuItemsRoom = menuItemsNetwork.map{it.toMenuItemRoom()}
        database.menuDao().insertAll(*menuItemsRoom.toTypedArray())

    }
         private val database by lazy {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database").build()
    }

    // Create and configure the HTTP client instance
    private val client= HttpClient(Android) {
        // Install ContentNegotiation

        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys= true

            })

        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Triggers the fetch
        lifecycleScope.launch(Dispatchers.IO) {
            if (database.menuDao().isEmpty()) {
                val menuItemNetwork= fetchMenu()
                saveMenuToDatabase(menuItemNetwork)
            }
        }

        setContent {

            LittleLemonTheme {

                var searchPhrase by remember { mutableStateOf("") }
                var selectedCategory by remember { mutableStateOf("")}

                // Observe menu items from Room
                val databaseMenuItems by database.menuDao()
                    .getAll()
                    .observeAsState(initial= emptyList())

                // Extract categories names from databaseMenuItems
                val categories = databaseMenuItems.map{it.category}.distinct()

                // Display category breakdown buttons

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(vertical = 8.dp)

                ) {
                    items(categories) { category ->
                        Button(
                            onClick = {
                                // toggle selection if already selected
                                selectedCategory = if(selectedCategory == category) "" else category
                            }
                        ) {
                            Text(text = category)
                        }
                    }
                }


                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {


                        TextField(
                            value = searchPhrase,
                            onValueChange = { searchPhrase = it },
                            placeholder = { Text("Enter Search Phrase") }
                        )

                        val filteredMenuItems = databaseMenuItems.filter { menuItems ->
                            val matchesSearch = searchPhrase.isEmpty() || menuItems.title.contains(
                                searchPhrase,
                                ignoreCase = true
                            )
                            val matchesCategory = selectedCategory.isEmpty() || menuItems.category.equals(
                                    selectedCategory,
                                    ignoreCase = true
                                )

                            matchesSearch && matchesCategory
                        }

                        MenuItems(menuItems = filteredMenuItems)
                    }
                }


                    val navController = rememberNavController()

                    Box(modifier = Modifier.padding()) {
                        Navigation(navController = navController)
                    }

                }
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
    LittleLemonTheme {
        Greeting("Android")
    }
}}