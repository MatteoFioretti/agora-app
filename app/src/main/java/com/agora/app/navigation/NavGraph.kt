package com.agora.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.agora.app.screens.*

@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = "login",
        modifier = modifier
    ) {
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(BottomNavItem.Explore.route) {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }
        composable(BottomNavItem.Explore.route) { ExploreScreen() }
        composable(BottomNavItem.OfferHelp.route) { OfferHelpScreen() }
        composable(BottomNavItem.Meetings.route) { MeetingsScreen() }
        composable(BottomNavItem.Profile.route) { ProfileScreen() }
    }
}