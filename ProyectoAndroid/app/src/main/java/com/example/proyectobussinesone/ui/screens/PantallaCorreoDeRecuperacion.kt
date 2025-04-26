package com.example.proyectobussinesone.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.proyectobussinesone.R
import com.example.proyectobussinesone.ui.theme.ProyectoBussinesOneTheme


@Composable
fun PantallaCorreoDeRecuperacion(navController: NavHostController) {
    val interactionSourceForgetPasword = remember { MutableInteractionSource() }
    val interactionSourceRegister = remember { MutableInteractionSource() }
    var mensajeEnviado = remember { mutableStateOf(false) }
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

        if (!mensajeEnviado.value){
            LabeleInput("Correo de recuperacion", "Correo electronico")
        }else{
            LabeleInput("Correo enviado", "Introduce el codigo de verificacion")
        }



        Button(
            onClick = {
                if (!mensajeEnviado.value) {
                    mensajeEnviado.value = true
                } },
            modifier = Modifier
                .width(210.dp)
                .height(74.dp),

            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF81D4FA)
            )
        ) {
            Icon(
                imageVector = Icons.Filled.Email,
                contentDescription = "Correo",
                tint = Color(0xFFFFFFFF), // azul, como un link si quieres
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.size(5.dp))
            Text("Enviar correo", color = Color.White,style = MaterialTheme.typography.h5 )
        }

        Spacer(modifier = Modifier.size(15.dp))
        Button(
            onClick = { navController.navigate("pantalla_iniciar_sesion")

                      },
            modifier = Modifier
                .width(190.dp)
                .height(64.dp),

            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF81D4FA)
            )
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Atrás",
                tint = Color.White, // Puedes cambiarlo por el color que necesites
                modifier = Modifier
                    .size(22.dp)
            )
            Spacer(modifier = Modifier.size(5.dp))
            Text("Volver atras", color = Color.White,style = MaterialTheme.typography.h5 )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewPantallaCorreoDeRecuperacion() {
    ProyectoBussinesOneTheme {
        PantallaCorreoDeRecuperacion(navController = rememberNavController())
    }
}