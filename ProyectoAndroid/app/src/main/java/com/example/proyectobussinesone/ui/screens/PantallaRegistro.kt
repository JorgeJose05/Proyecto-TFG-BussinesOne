package com.example.proyectobussinesone.ui.screens


import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.proyectobussinesone.R
import com.example.proyectobussinesone.RetrofitClient
import com.example.proyectobussinesone.ui.theme.ProyectoBussinesOneTheme
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
// ...etc


@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PantallaRegistro(navController: NavHostController) {
    val interactionSourceForgetPasword = remember { MutableInteractionSource() }
    val interactionSourceRegister = remember { MutableInteractionSource() }
    val context = LocalContext.current

    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }

    // Dropdown de roles
    val roles = listOf("Administrador", "Usuario")
    var rolSeleccionado by remember {  mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()


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

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        // Email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        // Contraseña
        OutlinedTextField(
            value = contrasena,
            onValueChange = { contrasena = it },
            label = { Text("Contraseña") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        //Rol
        RolDropdown(
            roles = roles,
            selectedRole = rolSeleccionado,
            onRoleSelected = { rolSeleccionado = it },
            modifier =  Modifier.fillMaxWidth(),
        )



        Spacer(Modifier.height(16.dp))


        Button(
            onClick = {  registrarUsuario(
                correo = email,
                contrasena = contrasena,
                rol = rolSeleccionado,
                nombre = nombre,
                onSuccess = { navController.navigate("landing_page") },
                onError = { errorMsg -> Log.e("Registro", errorMsg) }, // puedes mostrar un Toast o Snackbar aquí
                context = context
                )},
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

@Composable
fun RolDropdown(
    roles: List<String>,
    selectedRole: String,
    onRoleSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier
        .fillMaxWidth()
        .heightIn(min = 56.dp)              // Igual a la altura de un OutlinedTextField
        .border(
            width = 1.dp,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
            shape = RoundedCornerShape(4.dp)
        )
        .clip(RoundedCornerShape(4.dp))
        .clickable { expanded = true }
        .padding(horizontal = 16.dp),        // Igual que TextField internamente
        contentAlignment = Alignment.CenterStart) {
        // Este Text actúa como “selector”:
        Text(
            text = if (selectedRole.isEmpty()) "Selecciona un rol" else selectedRole,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }

        )

        DropdownMenu(
              expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            roles.forEach { rol ->
                DropdownMenuItem(
                    onClick = {
                        onRoleSelected(rol)
                        expanded = false
                    }
                ) {
                    Text(rol)
                }
            }
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

data class PerfilRequest(
    @SerializedName("nombre"    ) val nombre: String,
    @SerializedName("rol"       ) val rol: String,
    @SerializedName("email"     ) val email: String,
    @SerializedName("contraseña") val contrasena: String
)

fun registrarUsuario(correo: String, contrasena: String, rol: String, nombre: String, onSuccess: () -> Unit, onError: (String) -> Unit, context: android.content.Context) {
    val perfil = PerfilRequest(
        nombre     = nombre,
        rol        = rol,
        email      = correo,
        contrasena = contrasena
    )
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = RetrofitClient.moduloApiService.crearPerfil(perfil)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val nuevoPerfil = response.body()
                    // 🔐 Guardar ID en SharedPreferences
                    if (nuevoPerfil != null) {
                        val sharedPref = context.getSharedPreferences("UsuarioPrefs", Context.MODE_PRIVATE)
                        with(sharedPref.edit()) {
                            putLong("userId", nuevoPerfil.id)
                            apply()
                        }
                    }
                    onSuccess()
                } else {
                    onError("Error: ${response.code()}")
                    Log.e("Registro", "Error ${response.code()} -> "+response.errorBody()?.string().orEmpty())
                    Log.d(TAG, response.code().toString())
                }
            }
        } catch (e: Exception) {
            Log.d(TAG, e.message.toString())
            withContext(Dispatchers.Main) {
                onError("Excepción: ${e.localizedMessage}")
            }
        }
    }
}
