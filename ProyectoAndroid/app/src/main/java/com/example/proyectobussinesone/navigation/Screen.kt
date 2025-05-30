// 2) Define tus rutas y pestañas
package com.example.proyectobussinesone.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.proyectobussinesone.ui.screens.PantallaIniciarSesion

sealed class Screen(val route: String, val title: String,val icon: ImageVector) {
    object Home     : Screen("modulos",     "Modulos",  Icons.Filled.Apps)
    object Profile  : Screen("profile",  "Perfil", Icons.Filled.Person)
  }

val tabs = listOf(
    Screen.Home,
    Screen.Profile,
)
