package com.littlelemon.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage


@Composable
fun Home(navController: NavController) {

    val context = LocalContext.current
    val isInPreview = LocalInspectionMode.current

    val database = remember {
        AppDatabase.getInstance(context)
    }

    var searchPhrase by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("") }

    // Observe menu items from Room
    val liveDataMenuItems by database.menuDao()
        .getAll()
        .observeAsState(initial = emptyList())

    // Use Mock data if Preview and live data is empty

    val databaseMenuItems = if (isInPreview && liveDataMenuItems.isEmpty()) {
        listOf(
            MenuItemEntity(1, "Greek Salad", 10, "", "starters", "Crispy lettuce, peppers, olives"),
            MenuItemEntity(2, "Bruschetta", 8, "", "starters", "Grilled bread with garlic"),
            MenuItemEntity(3, "Grilled Fish", 15, "", "mains", "Mediterranean grilled fish"),
            MenuItemEntity(4, "Lemon Dessert", 10, "", "desserts", "Traditional homemade Italian Lemon Ricotta Cake.")
        )
    } else {
        liveDataMenuItems
    }

    // Extract categories names from databaseMenuItems
    val categories = databaseMenuItems.map { it.category }.distinct()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .fillMaxHeight(.1f)
                .background(Color.White)
        ) {
            // Logo in the center
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Little Lemon Logo",
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(8.dp)
                    .size(150.dp, 150.dp)
            )

            //1. Profile picture on the extreme right
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "Profile",
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(16.dp)
                    .size(48.dp)
                    .clip(CircleShape)
                    .clickable {
                        navController.navigate(Profile.route)
                    }
            )
        }

        //2. Hero Section Column

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.5f)
                .background(color = (Color(0xFF495E57)))
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)

            ) {
                Text(
                    text = "Little Lemon",
                    fontWeight = FontWeight.Bold,
                    fontSize = 36.sp,
                    color = Color(0xFFF4CE14)
                )

                Text(
                    text = "Chicago",
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp,
                    color = Color.White
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.Absolute.Left

                ) {
                    Text(
                        text = "We are a family-owned Mediterranean restaurant, focused on traditional recipes served with a modern twist.",
                        fontSize = 16.sp,
                        color = Color.White,
                        modifier = Modifier.weight(1f)
                    )

                    Image(
                        painter = painterResource(id = R.drawable.heroimage),
                        contentDescription = "Hero Image",
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .align(Alignment.Top)
                                            )
                }
                //3. Search Bar

                TextField(
                    value = searchPhrase,
                    colors = TextFieldDefaults.colors(
                      focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),

                    onValueChange = { searchPhrase = it },
                    textStyle = TextStyle(color = Color.Black),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Icon",
                            tint = Color.Gray
                        )
                    },
                    placeholder = {
                        Text(
                            text= "Enter Search Phrase",
                            color= Color.Gray
                        )
                                  },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                )

            }

        }

        //4. Category filter Button (Lazy Row)
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(vertical = 8.dp)

        ) {
            items(categories) { category ->
                Button(
                    onClick = {
                        // toggle selection if already selected
                        selectedCategory = if (selectedCategory == category) "" else category
                    }
                ) {
                    Text(text = category)
                }
            }
        }

        // 5. Filtered Menu Item List
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

        MenuList(
            menuItems = filteredMenuItems,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(8.dp)

        )

    }
}


@Composable
fun MenuList(
    menuItems: List<MenuItemEntity>,
    modifier: Modifier = Modifier
    ) {
    LazyColumn(
           modifier = modifier


    ) {
        items(
            items = menuItems,
            itemContent = { menuItem ->
                MenuItemRow(
                    title = menuItem.title,
                    price = menuItem.price,
                    category = menuItem.category,
                    image = menuItem.image,
                    description = menuItem.description
                )
            }
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItemRow(
    title: String,
    price: Int,
    category: String,
    image: String,
    description: String
) {

    val isInPreview = LocalInspectionMode.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                fontSize = 18.sp,
                color = Color.Black
            )
            Text(
                text = description ,
                fontSize = 14.sp,
                color = Color.Gray,
                maxLines = 2,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Text(
                text = "$$price",
                fontSize = 16.sp,
                color = Color.DarkGray
            )
        }

        if (isInPreview || image.isBlank()) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = title,
                modifier = Modifier
                    .size(80.dp)
                    .padding(start = 8.dp)
            )
        } else {
            GlideImage(
                model = image,
                contentDescription = title,
                modifier = Modifier
                    .size(80.dp)
                    .padding(start = 8.dp)
            )
        }


    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    val navController = rememberNavController()
    Home(navController = navController)

}
