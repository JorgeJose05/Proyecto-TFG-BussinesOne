package com.example.proyectobussinesone



import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.material.Surface
import androidx.navigation.compose.rememberNavController
import com.example.proyectobussinesone.ui.screens.PantallaIniciarSesion
import com.example.proyectobussinesone.ui.screens.PantallaDetalle




import androidx.compose.runtime.Composable
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.fillMaxSize
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectobussinesone.ui.screens.PantallaCorreoDeRecuperacion
import com.example.proyectobussinesone.ui.screens.PantallaRegistro
import com.example.proyectobussinesone.ui.screens.PreviewPantallaCorreoDeRecuperacion
import com.example.proyectobussinesone.ui.theme.ProyectoBussinesOneTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProyectoBussinesOneTheme() {
                // Paso 3: Crea el NavController
                val navController = rememberNavController()
                Surface(modifier = Modifier.fillMaxSize()) {
                    // Paso 4: Configura el NavHost con rutas
                    NavHost(
                        navController = navController,
                        startDestination = "pantalla_iniciar_sesion"
                    ) {
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
                    }
                }
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun PreviewPantallaPrincipal() {
    ProyectoBussinesOneTheme {
       PantallaIniciarSesion(navController = rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPantallaDetalle() {
    ProyectoBussinesOneTheme {
       PantallaDetalle(navController = rememberNavController())
    }
}

