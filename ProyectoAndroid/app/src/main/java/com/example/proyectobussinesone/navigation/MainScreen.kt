package com.example.proyectobussinesone.navigation


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.proyectobussinesone.ui.screens.PantallaIniciarSesion
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.material.Icon
import androidx.compose.material.TabRow
import androidx.compose.ui.platform.LocalContext
import com.example.proyectobussinesone.LandingPage
import com.example.proyectobussinesone.MainActivity
import com.example.proyectobussinesone.ui.screens.PantallaCorreoDeRecuperacion
import com.example.proyectobussinesone.ui.screens.PantallaDetalle
import com.example.proyectobussinesone.ui.screens.PantallaRegistro
import com.example.proyectobussinesone.ui.theme.ProyectoBussinesOneTheme


@Composable
fun MainScreen(navController2 : NavHostController) {
    // 3.1 NavController
    val navController = rememberNavController()
    // 3.2 Para saber qué pestaña va activa
    val backStack by navController.currentBackStackEntryAsState()
    val currentRoute = backStack?.destination?.route

    val userId = MainActivity.UsuarioSesion.getUserId(LocalContext.current)


    Scaffold(
        bottomBar = {
            TabRow(
                selectedTabIndex = tabs.indexOfFirst { it.route == currentRoute }
                    .coerceAtLeast(0),
                backgroundColor = Color.White,       // Fondo personalizado
                contentColor    = Color(0xFF2222FF),

            ) {
                tabs.forEach { screen ->
                    Tab(
                        icon = {
                            Icon(
                                imageVector        = screen.icon,
                                contentDescription = screen.title,
                                modifier           = Modifier.size(28.dp),
                                tint               = if (screen.route == currentRoute)
                                    Color.Blue
                                else
                                    Color.Black
                            )
                        },
                        text = { Text(screen.title, color = Color.Black) },
                        selected = screen.route == currentRoute,
                        onClick = {
                            if (screen.route != currentRoute) {
                                navController.navigate(screen.route) {
                                    // Evita duplicar la misma pantalla
                                    launchSingleTop = true
                                    // Mantén el estado de las otras pestañas
                                    restoreState = true
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                }
                            }
                        },
                        selectedContentColor =  Color(0xFF4d6AFF),      // Color para la pestaña seleccionada
                        unselectedContentColor = Color.DarkGray,//Cuando seleccionas otro el icono que estaba seleccionado se pone de este color un segundo
                    )
                }
            }
        }
    ) { innerPadding ->
        // 3.3 Contenido que cambia según la pestaña
        NavHost(
            navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route)     { ModulosScreen() }
            composable(Screen.Profile.route) { backStack ->
                ProfileScreen(
                    navController = navController,
                    usuarioId    = userId
                )
            }
            composable("pantalla_iniciar_sesion")  { PantallaIniciarSesion(navController2) }
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
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    ProyectoBussinesOneTheme {
        MainScreen(navController2 = rememberNavController())
    }
}
