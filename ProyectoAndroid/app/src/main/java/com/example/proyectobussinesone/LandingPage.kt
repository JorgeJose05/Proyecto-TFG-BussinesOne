package com.example.proyectobussinesone

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectobussinesone.navigation.MainScreen
import com.example.proyectobussinesone.navigation.ModulosScreen
import com.example.proyectobussinesone.navigation.ProfileScreen
import com.example.proyectobussinesone.ui.screens.PantallaCorreoDeRecuperacion
import com.example.proyectobussinesone.ui.screens.PantallaDetalle
import com.example.proyectobussinesone.ui.screens.PantallaIniciarSesion
import com.example.proyectobussinesone.ui.screens.PantallaRegistro

sealed class Screen(val route: String, val title: String) {
    object Home : Screen("home", "Inicio")
    object Profile : Screen("profile", "Perfil")
}

val tabs = listOf(Screen.Home, Screen.Profile)


@Composable
fun LandingPage(navController: NavHostController) {
    MainScreen(navController2 = navController)

}
@Composable
fun AppNavHost(navController: NavHostController) {
    val userId = MainActivity.UsuarioSesion.getUserId(LocalContext.current)
    NavHost(navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) { ModulosScreen() }
        composable("pantalla_iniciar_sesion") {
            PantallaIniciarSesion(
                navController
            )
        }
        composable("landing_page") {
            LandingPage(
                navController
            )
        }
        composable("pantalla_detalle") {
            PantallaDetalle(navController)
        }
        composable("pantalla_registro") {
            PantallaRegistro(navController)
        }
        composable("pantalla_recuperacion_de_correo") {
            PantallaCorreoDeRecuperacion(navController)
        }
        composable(Screen.Profile.route) { backStack ->
            ProfileScreen(
                navController = navController,
                usuarioId    = userId
            )
        }

    }
}
