package com.littlelemon.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage


@Composable
fun MenuItems(menuItems: List<MenuItems>?) {

    Column() {


    }
@Composable

fun Home(navController: NavController) {


    Box(
        modifier = Modifier.fillMaxSize(),

    ) {
        // Logo in the center
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription= "Little Lemon Logo",
            modifier = Modifier.align(Alignment.Center)
        )

        // Profile picture on the extreme right

        Image(
            painter = painterResource(id= R.drawable.profile),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        )
    }



    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier.weight(1F)
        ) {
            Text(
                text= "Little Lemon",
                fontSize= 36.sp,
                color =  Color(0xFFF4CE14)
            )

            Text(
                text= "Chicago",
                fontSize = 26.sp,
                color = Color(0xFFEDEFEE)
            )

            Image(
                painter = painterResource(id = R.drawable.heroimage),
                contentDescription= "Hero Image",
                modifier = Modifier.weight(1F)

            )

            Text(
                text= " We are a family-owned Mediterranean restaurant, focused on traditional recipes served with a modern twist",
                fontSize = 22.sp,
                color = Color(0xFFEDEFEE)
            )
        }
    }

    }
    // Composable for single item

    @OptIn(ExperimentalGlideComposeApi::class)
    @Composable

    fun MenuItems(
        title: String,
        price: Int,
        description: String,
        image: String,
    ) {

        Column() {
            Text(text = title)
            Text(text = description)

            GlideImage(
                model= image,
                contentDescription = title
            )

        }

    }

    Button(
        onClick = {
            navController.navigate(Profile)
        }
    ) {
        Text(
            text= "Log out"
        )
    }
}