package com.example.proyectobussinesone.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.proyectobussinesone.ui.theme.ProyectoBussinesOneTheme

@Composable
fun PantallaDetalle(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        // Botón de retroceso
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text("¡Estás en la pantalla de detalle!", style = MaterialTheme.typography.h6)
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewPantallaDetalle() {
    ProyectoBussinesOneTheme {
        PantallaDetalle(navController = rememberNavController())
    }
}