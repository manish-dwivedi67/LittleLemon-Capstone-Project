package com.littlelemon.littlelemon

import android.content.Context
import android.text.Layout
import android.widget.GridLayout
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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
    val sharedPreferences= context.getSharedPreferences("User Prefs", Context.MODE_PRIVATE)
    val value1 = sharedPreferences.getString("FIRST_NAME", "")
    val value2 = sharedPreferences.getString("LAST_NAME", "")
    val value3 = sharedPreferences.getString("EMAIL", "")


    Box(
        modifier = Modifier.fillMaxSize(),
            )
    {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription= "Little Lemon Logo",
            modifier = Modifier.align(Alignment.Center)

        )

        Image(
            painter = painterResource(id=R.drawable.profile),
            contentDescription = "Profile Image",
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }

    Text(
        text= "Profile Information",
        fontSize = 24.sp,
        fontWeight = FontWeight.SemiBold,

    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value1 ?: ""
        )

        Text(
            text = value2 ?: ""
        )

        Text(
            text = value3 ?: ""
        )

        Button(
            shape = RoundedCornerShape(12.dp),
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
                text= "Log Out"
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








