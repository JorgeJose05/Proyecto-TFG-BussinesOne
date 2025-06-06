package com.example.proyectobussinesone.ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.proyectobussinesone.R
import com.example.proyectobussinesone.ui.theme.ProyectoBussinesOneTheme

@Composable
fun PantallaRegistro(navController: NavHostController) {
    val interactionSourceForgetPasword = remember { MutableInteractionSource() }
    val interactionSourceRegister = remember { MutableInteractionSource() }

    val email = remember { mutableStateOf("") }
    val contrasena = remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp), // define altura
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo), // tu imagen
                contentDescription = "Logo de la app",
                contentScale = ContentScale.Fit, // o Crop, FillWidth, etc.
                modifier = Modifier.fillMaxWidth(0.5f) // o tamaño personalizado
            )
        }

        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = contrasena.value,
            onValueChange = { contrasena.value = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth()
        )


        Button(
            onClick = { navController.navigate("landing_page") },
            modifier = Modifier
                .width(210.dp)
                .height(74.dp),

            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF81D4FA)
            )
        ) {
            Text("Registrarse", color = Color.White,style = MaterialTheme.typography.h5 )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
        ) {
            Text(
                text = "Ya tienes una cuenta?",
                style = MaterialTheme.typography.subtitle1
            )

            Text(
                text = "Iniciar sesion",
                color = Color(0xFF1976D2), // azul como link
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .clickable(
                        indication = null, // Elimina la animación ripple
                        interactionSource = interactionSourceRegister
                    ) {

                        navController.navigate("pantalla_iniciar_sesion")
                    }
                    .padding(start = 8.dp)
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewPantallaRegistro() {
    ProyectoBussinesOneTheme {
        PantallaRegistro(navController = rememberNavController())
    }
}