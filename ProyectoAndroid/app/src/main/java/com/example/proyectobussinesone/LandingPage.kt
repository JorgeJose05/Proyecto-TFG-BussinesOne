package com.example.proyectobussinesone

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectobussinesone.navigation.MainScreen
import com.example.proyectobussinesone.navigation.ModulosScreen
import com.example.proyectobussinesone.navigation.ProfileScreen
import com.example.proyectobussinesone.navigation.SettingsScreen

sealed class Screen(val route: String, val title: String) {
    object Home : Screen("home", "Inicio")
    object Profile : Screen("profile", "Perfil")
    object Settings : Screen("settings", "Ajustes")
}

val tabs = listOf(Screen.Home, Screen.Profile, Screen.Settings)


@Composable
fun LandingPage(navController: NavHostController) {
    MainScreen(navController = rememberNavController())

}
@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) { ModulosScreen() }
        composable(Screen.Profile.route) { ProfileScreen() }
        composable(Screen.Settings.route) { SettingsScreen() }
    }
}
