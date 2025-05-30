package com.example.proyectobussinesone.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import com.example.proyectobussinesone.R
import com.example.proyectobussinesone.ui.theme.ProyectoBussinesOneTheme

@Composable
fun PantallaIniciarSesion(navController: NavHostController) {
    val interactionSourceForgetPasword = remember { MutableInteractionSource() }
    val interactionSourceRegister = remember { MutableInteractionSource() }
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

        LabeleInput("Usuario", "Correo electronico")
        LabeleInput("Contraseña","Contraseña")


        Button(
            onClick = { navController.navigate("landing_page") },
            modifier = Modifier
                .width(210.dp)
                .height(74.dp),

            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF81D4FA)
            )
        ) {
            Text("Iniciar sesion", color = Color.White,style = MaterialTheme.typography.h5 )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
        ) {
            Text(
                text = "Aun no tiense una cuenta?",
                style = MaterialTheme.typography.subtitle1
            )

            Text(
                text = "Registrate",
                color = Color(0xFF1976D2), // azul como link
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .clickable(
                        indication = null, // Elimina la animación ripple
                        interactionSource = interactionSourceRegister
                    ) {

                        navController.navigate("pantalla_registro")
                    }
                    .padding(start = 8.dp)
            )
        }
    }
}

@Composable
fun LabeleInput(label : String, placeholder: String ){
    var texto by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ){

        Text(label, style = MaterialTheme.typography.h6)

        OutlinedTextField(
            value = texto,
            onValueChange = { texto = it },
            label = { Text(placeholder) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Black,      // Borde cuando activo
            focusedLabelColor = Color.Black,       // Label cuando activo
            cursorColor = Color.Black,             // Color del cursor
            textColor = Color.Black                // Color del texto
        )
        )
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewPantallaPrincipal() {
    ProyectoBussinesOneTheme {
        PantallaIniciarSesion(navController = rememberNavController())
    }
}

