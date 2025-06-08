package com.example.proyectobussinesone.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyectobussinesone.MainActivity
import com.example.proyectobussinesone.R
import com.example.proyectobussinesone.RetrofitClient
import com.example.proyectobussinesone.navigation.PerfilRepository
import com.example.proyectobussinesone.ui.theme.ProyectoBussinesOneTheme
import com.example.proyectobussinesone.ui.viewmodel.PerfilViewModel

@Composable
fun PantallaIniciarSesion(navController: NavHostController) {
    val interactionSourceForgetPasword = remember { MutableInteractionSource() }
    val interactionSourceRegister = remember { MutableInteractionSource() }

    var emailInput by remember { mutableStateOf("") }
    var passwordInput by remember { mutableStateOf("") }
    var loginAttempted by remember { mutableStateOf(false) }
    val userId = MainActivity.UsuarioSesion.getUserId(LocalContext.current)


    // Creamos repositorio y ViewModel (sin Hilt)
    val repository = remember { PerfilRepository(RetrofitClient.moduloApiService) }
    val factory = remember { PerfilViewModel.Factory(repository, usuarioId = userId) }
    val vm: PerfilViewModel = viewModel(
        viewModelStoreOwner = LocalViewModelStoreOwner.current!!,
        factory = factory
    )

    // Observamos los estados desde el ViewModel
    val loginState by vm.loginState.collectAsState()
    val errorMsg by vm.errorMessage.collectAsState()
    val context = LocalContext.current;

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFf0f8ff))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFf0f8ff))
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

        LabeledInput(
            label = "Usuario",
            placeholder = "Correo electrónico",
            value = emailInput,
            onValueChange = { emailInput = it }
        )
        // Input para contraseña
        LabeledInput(
            label = "Contraseña",
            placeholder = "Contraseña",
            value = passwordInput,
            onValueChange = { passwordInput = it },
            isPassword = true
        )

        Button(
            onClick = {
                loginAttempted = true
                vm.login(context, emailInput.trim(), passwordInput.trim())
            },
            modifier = Modifier
                .width(210.dp)
                .height(74.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF81D4FA)
            )
        ) {
            Text("Iniciar sesión", color = Color.White, style = MaterialTheme.typography.h5)
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
        // Mostramos indicador de carga o error, o navegamos si ya llegó el perfil
        if (loginAttempted) {
            when (loginState) {
                PerfilViewModel.LoginState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        androidx.compose.material.CircularProgressIndicator()
                    }
                }
                is PerfilViewModel.LoginState.Success -> {
                    // Si coincide email y contraseña, navegamos
                    LaunchedEffect(loginState) {
                        navController.navigate("landing_page")
                    }
                }
                is PerfilViewModel.LoginState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = (loginState as PerfilViewModel.LoginState.Error).message,
                            color = Color.Red
                        )
                    }
                }
                else -> { /* no hace nada */ }
            }
        }
    }
}

@Composable
fun LabeledInput(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(label, style = MaterialTheme.typography.h6)

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(placeholder) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Black,
                focusedLabelColor = Color.Black,
                cursorColor = Color.Black,
                textColor = Color.Black
            ),
            visualTransformation = if (isPassword) androidx.compose.ui.text.input.PasswordVisualTransformation() else androidx.compose.ui.text.input.VisualTransformation.None
        )
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

