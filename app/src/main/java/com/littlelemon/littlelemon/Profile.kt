package com.littlelemon.littlelemon

import android.content.Context
import android.text.Layout
import android.widget.GridLayout
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import org.intellij.lang.annotations.JdkConstants


@Composable
fun Profile(navController: NavController) {

    val context = LocalContext.current

// Retrieve value from shared preferences stored in the Onboarding file
    val sharedPreferences = context.getSharedPreferences("User Prefs", Context.MODE_PRIVATE)
    val value1 = sharedPreferences.getString("FIRST_NAME", "")
    val value2 = sharedPreferences.getString("LAST_NAME", "")
    val value3 = sharedPreferences.getString("EMAIL", "")


    Column(
        modifier = Modifier.fillMaxSize()
            .background(color = Color.White)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(.15f)
        )
        {

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Little Lemon Logo",
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(width = 250.dp, height = 100.dp)
                    .padding(16.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(16.dp)
                    .size(40.dp)

            )


        }

        Text(
            text = "Personal Information",
            color = Color(0xFF293845),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, top = 30.dp, bottom = 20.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            TextField(// First Name
                value = value1 ?: "",
                onValueChange = {},
                label = {Text("First Name")},
                modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp),
                shape = RoundedCornerShape(8.dp),
                readOnly = true

            )

            TextField(// Last Name
                value = value2 ?: "",
                onValueChange = {},
                label = {Text("Last Name")},
                modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp),
                shape = RoundedCornerShape(8.dp),
                readOnly = true

            )

            TextField(// E Mail
                value = value3 ?: "",
                onValueChange = {},
                label = {Text("E Mail")},
                modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp),
                shape = RoundedCornerShape(8.dp),
                readOnly = true

            )

        }


        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top= 90.dp, bottom = 30.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Yellow,
                contentColor = Color.Black
            ),
            onClick = {
                //1. Clear stored shared preferences
                sharedPreferences.edit().clear().apply()

                //2.Navigatye back to Onboarding Screen
                navController.navigate(Onboarding.route)

            }
        ) {
            Text(
                text = "Log Out"
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    val navController = rememberNavController()
     Profile(navController = navController)
}








