package com.littlelemon.littlelemon

import android.graphics.Paint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController


@Composable

fun Home(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription= "Little Lemon Logo"
        )
    }

    Image(
        painter = painterResource(id= R.drawable.profile),
        contentDescription = "Profile Picture",
    )

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