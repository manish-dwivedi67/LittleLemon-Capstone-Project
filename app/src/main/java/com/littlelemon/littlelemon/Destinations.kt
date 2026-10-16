package com.littlelemon.littlelemon




interface Destinations  {
    val route: String

}
// Create objects for each screen with Destinations interface

object Onboarding: Destinations{
    override val route: String= "Onboarding"

}

object HomeDestination: Destinations {

    override val route: String = "Home"
}

object Profile: Destinations {
    override val route: String = "Profile"

}

