package com.littlelemon.littlelemon

import android.content.Context

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.navigation.NavController

@Preview (showBackground = true)
@Composable

fun Onboarding(navController: NavController) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Little Lemon Logo"
        )

        //Add static text for the user
        Text(
            text = "Let's get to know you",
            color = Color.White,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .background(Color(0xFF495E57))
                .fillMaxWidth()
                .padding(vertical = 40.dp)
        )

        Text(
            text= "Personal Information"
        )

        Column {
            TextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text("First Name") },
                modifier = Modifier
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),


            )

            TextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text("Last Name") },
                modifier = Modifier
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
            )

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("E Mail Address") },
                modifier = Modifier
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
            )
            var registrationMessage by remember { mutableStateOf("") }

            Button(
                onClick = {
                    if (firstName.isBlank() || lastName.isBlank() || email.isBlank() ) {
                        registrationMessage = "Registration unsuccessful. Please enter all data."
                    }


                    } else {
                        // Creating shared prefernces for storing user's data
                        val sharedPreferences= getSharedPreferences("User Prefs", Context.MODE_PRIVATE)

                        with (sharedPreferences.edit()
                            .putString("FIRST_NAME", firstName)
                            .putString("LAST_NAME", lastName)
                            .putString("EMAIL", email)
                                .commit()

                registrationMessage = "Registration Successfully"
                navController.navigate("Home")

                    }

                }
                modifier = Modifier.background(Color(0xFFF4CE14))
            ) { Text("Register")


            }
        Text(text = registrationMessage)
        }
    }
}


