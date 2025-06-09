package com.example.proyectobussinesone.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.proyectobussinesone.ModuloStoreItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModuloDetailScreen(
    modulo: ModuloStoreItem,
    onBack: () -> Unit
) {
    var installed by rememberSaveable { mutableStateOf(modulo.nombre == "Fichaje" || modulo.nombre == "Punto de venta") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(modulo.nombre) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor    = Color(0xFF4FC3F7),
                    titleContentColor = Color.White
                )
            )
        }
    ) { inner ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(inner)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = modulo.icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(64.dp)
            )
            Text(
                text = modulo.nombre,
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "Categoría: ${modulo.categoria}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = modulo.descripcion,
                style = MaterialTheme.typography.bodySmall
            )

            Button(
                onClick = {
                    installed = !installed
                    // Actualiza SOLO en memoria:
                    modulo.disponible = !installed
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (installed) Color.Red else Color(0xFF4FC3F7),
                    contentColor   = Color.White
                )
            ) {
                Text(if (installed) "Desinstalar" else "Instalar")
            }
        }
    }
}
