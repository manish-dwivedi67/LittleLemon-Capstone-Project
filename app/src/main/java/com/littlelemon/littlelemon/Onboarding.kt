package com.littlelemon.littlelemon

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.indication
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.text.font.FontWeight

@Composable
fun Onboarding(navController: NavHostController = rememberNavController()) {
    val context = LocalContext.current

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.15f)
                .background(Color.White)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Little Lemon Logo",
                modifier = Modifier
                    .fillMaxHeight(0.5f)
                    .fillMaxWidth(0.6f)
            )
        }

        // Add static text for the user
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
            text = "Personal Information",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF293845),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, top = 30.dp, bottom = 20.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            // First Name
            TextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text("First Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding( bottom = 16.dp),
                shape = RoundedCornerShape(8.dp),
                singleLine = true
            )

            // Last Name
            TextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text("Last Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp, top = 12.dp),
                shape = RoundedCornerShape(8.dp),
                singleLine = true
            )

            // Email
            TextField( // Outlined box for email
                value = email,
                onValueChange = { email = it },
                label = { Text("E Mail") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp, top = 12.dp),
                shape = RoundedCornerShape(8.dp),
                singleLine = true
                )
            var registrationMessage by remember { mutableStateOf("") }

            Button(
                 onClick = {
                    if (firstName.isBlank() || lastName.isBlank() || email.isBlank()) {
                        registrationMessage = "Registration unsuccessful. Please enter all data."
                    } else {
                        // Creating shared preferences for storing user's data
                        val sharedPreferences = context.getSharedPreferences("User Prefs", Context.MODE_PRIVATE)

                        sharedPreferences.edit()
                            .putString("FIRST_NAME", firstName)
                            .putString("LAST_NAME", lastName)
                            .putString("EMAIL", email)
                            .apply()
                        registrationMessage = "Registration Successfully"
                        navController.navigate("Home")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp, bottom = 30.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF4CE14),
                    contentColor = Color.Black
                )

            ) {
                Text(
                    text = "Register",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

            }
            Text(text = registrationMessage)

        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingPreview() {
    Onboarding(rememberNavController())
}



