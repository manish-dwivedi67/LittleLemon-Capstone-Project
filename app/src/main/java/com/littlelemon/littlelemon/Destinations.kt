package com.littlelemon.littlelemon

import androidx.navigation.Navigation
import androidx.navigationevent.NavigationEventInfo

interface Destinations  {
    val route: String

}
// Create objects for each screen with Destinations interface

object Onboarding: Destinations{
    override val route: String= "Onboarding"

}

object Home: Destinations {

    override val route: String = "Home"
}

object Profile: Destinations {
    override val route: String = "Profile"

}

