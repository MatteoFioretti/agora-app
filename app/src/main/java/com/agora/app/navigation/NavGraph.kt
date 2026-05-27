package com.agora.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.agora.app.data.FakeData
import com.agora.app.screens.*

@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = "welcome",
        modifier = modifier
    ) {
        composable("welcome") {
            WelcomeScreen(
                onContinue = {
                    navController.navigate(BottomNavItem.Explore.route) {
                        popUpTo("welcome") { inclusive = true }
                    }
                }
            )
        }

        composable(BottomNavItem.Explore.route) {
            ExploreScreen(
                onRequestConversation = { studentId ->
                    navController.navigate("book_session/$studentId")
                }
            )
        }
        composable(BottomNavItem.OfferHelp.route) { OfferHelpScreen() }
        composable(BottomNavItem.Meetings.route) {
            MeetingsScreen(
                onConfirmSession = { meetingId ->
                    navController.navigate("confirm_session/$meetingId")
                }
            )
        }
        composable(BottomNavItem.Profile.route) { ProfileScreen() }
        composable(
            route = "confirm_session/{meetingId}",
            arguments = listOf(navArgument("meetingId") { type = NavType.IntType })
        ) { backStackEntry ->
            val meetingId = backStackEntry.arguments?.getInt("meetingId") ?: 1
            val meeting = FakeData.meetings.find { it.id == meetingId }
            if (meeting != null) {
                ConfirmSessionScreen(
                    meeting = meeting,
                    onBack = { navController.popBackStack() }
                )
            }
        }
        composable(
            route = "book_session/{studentId}",
            arguments = listOf(navArgument("studentId") { type = NavType.IntType })
        ) { backStackEntry ->
            val studentId = backStackEntry.arguments?.getInt("studentId") ?: 1
            val student = FakeData.allStudents.find { it.id == studentId }
            if (student != null) {
                BookingScreen(
                    student = student,
                    onBack = { navController.popBackStack() },
                    onRequestSent = {
                        navController.navigate(BottomNavItem.Meetings.route) {
                            popUpTo(BottomNavItem.Explore.route) {
                                inclusive = false
                            }
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}