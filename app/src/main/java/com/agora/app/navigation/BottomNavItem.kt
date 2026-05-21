package com.agora.app.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.VolunteerActivism
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    object Explore : BottomNavItem("explore", "Explore", Icons.Outlined.Explore)
    object OfferHelp : BottomNavItem("offer_help", "Offer Help", Icons.Outlined.VolunteerActivism)
    object Meetings : BottomNavItem("meetings", "Meetings", Icons.Outlined.CalendarMonth)
    object Profile : BottomNavItem("profile", "Profile", Icons.Outlined.Person)
}