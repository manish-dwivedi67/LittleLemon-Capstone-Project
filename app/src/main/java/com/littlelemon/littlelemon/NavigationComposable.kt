package com.littlelemon.littlelemon

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost

import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

//Define the function navigation Composable


@Composable

fun Navigation(navController: NavHostController) {

    val context= LocalContext.current

    val sharedPreferences= context.getSharedPreferences("User Prefs", Context.MODE_PRIVATE)

    val firstName= sharedPreferences.getString("FIRST_NAME", null)

    val isRegistered = firstName != null

    val startDestination = if(isRegistered) {"Home" } else {"Onboarding" }

    NavHost(
        navController= navController,
        startDestination= startDestination
    ) {
        composable(Onboarding.route) {
            Onboarding(navController= navController)
        }

        composable(Home.route) {
            Home(navController)
        }

        composable(Profile.route) {
            Profile(navController)
        }


    }

}


