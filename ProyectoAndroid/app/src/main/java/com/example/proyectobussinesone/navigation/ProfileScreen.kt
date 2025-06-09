package com.example.proyectobussinesone.navigation



import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.proyectobussinesone.R
import com.example.proyectobussinesone.ui.theme.AzulSecundario
import com.example.proyectobussinesone.ui.theme.FondoClaro
import com.example.proyectobussinesone.ui.theme.ProyectoBussinesOneTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.proyectobussinesone.RetrofitClient
import com.example.proyectobussinesone.ui.viewmodel.PerfilViewModel

@Composable
fun AcademicSection(
    initialFormacion: String,
    onFormacionChange: (String) -> Unit
) {
    SectionTitle("Formación Académica y Certificaciones")

    var formacion by remember { mutableStateOf(initialFormacion) }
    var isEditing by remember { mutableStateOf(initialFormacion.isBlank()) }

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        if (isEditing) {
            OutlinedTextField(
                value = formacion,
                onValueChange = {
                    formacion = it
                    onFormacionChange(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 120.dp),
                placeholder = { Text("Introduce tu formación académica") },
                singleLine = false,
                maxLines = 5
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { isEditing = false }) {
                    Icon(Icons.Default.Check, contentDescription = "Guardar formación")
                }
            }
        } else {
            Text(
                text = if (formacion.isBlank()) "Sin información" else formacion,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
            IconButton(onClick = { isEditing = true }) {
                Icon(Icons.Default.Edit, contentDescription = "Editar formación")
            }
        }
    }

    Spacer(modifier = Modifier.height(24.dp))
}

@Composable
fun EditableAcademicItem(
    initialValue: String,
    onValueSaved: (String) -> Unit,
    onDelete: () -> Unit
) {
    var isEditing by remember { mutableStateOf(initialValue.isBlank()) }
    var text by remember { mutableStateOf(initialValue) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isEditing) {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Introduce formación académica") },
                singleLine = false
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = {
                isEditing = false
                onValueSaved(text)
            }) {
                Icon(Icons.Default.Check, contentDescription = "Guardar")
            }
        } else {
            Text(
                text = if (text.isBlank()) "Sin información" else "• $text",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { isEditing = true }) {
                Icon(Icons.Default.Edit, contentDescription = "Editar")
            }
            IconButton(onClick = { onDelete() }) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar")
            }
        }
    }
}


@Composable
fun PersonalDataSection() {
    SectionTitle("Datos Personales")


    var dni by remember { mutableStateOf<String?>(null) }
    var nacimiento by remember { mutableStateOf<String?>(null) }
    var ss by remember { mutableStateOf<String?>(null) }
    var iban by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        EditableInfoRow(
            icon = Icons.Default.Badge,
            label = "DNI",
            value = dni,
            onValueChange = { dni = it }
        )
        EditableInfoRow(
            icon = Icons.Default.Cake,
            label = "Fecha de Nacimiento",
            value = nacimiento,
            onValueChange = { nacimiento = it }
        )
        EditableInfoRow(
            icon = Icons.Default.Security,
            label = "Nº Seguridad Social",
            value = ss,
            onValueChange = { ss = it }
        )
        EditableInfoRow(
            icon = Icons.Default.AccountBalance,
            label = "IBAN",
            value = iban,
            onValueChange = { iban = it }
        )
    }

    Spacer(modifier = Modifier.height(24.dp))
}

@Composable
fun ProfileScreen(usuarioId: Long, navController: NavHostController) {
    // Necesitamos un ViewModelProvider.Factory
    val repository = remember { PerfilRepository(RetrofitClient.moduloApiService) }
    val factory = remember { PerfilViewModel.Factory(repository, usuarioId) }

    val context = LocalContext.current
    // Usamos viewModel(factory = ...) para crear/recuperar el ViewModel
    val vm: PerfilViewModel = viewModel(
        viewModelStoreOwner = LocalViewModelStoreOwner.current!!,
        factory = factory
    )

    // Observamos el estado
    val perfil by vm.perfilState.collectAsState()
    val errorMsg by vm.errorMessage.collectAsState()


    // Mostramos mientras carga o error
    when {
        perfil == null && errorMsg == null -> {
            // Cargando
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        perfil == null && errorMsg != null -> {
            // Mostrar mensaje de error
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = errorMsg ?: "Error desconocido", color = Color.Red)
            }
        }
        else -> {
            // Ya tenemos datos de perfil: perfil != null
            val datos = perfil!!
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "Foto de perfil",
                            modifier = Modifier
                                .size(120.dp)
                                .clip(CircleShape)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = datos.nombre, style = MaterialTheme.typography.headlineSmall)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = datos.email, color = Color.Gray)
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }

                item {
                    SectionTitle("Contacto")
                    EditableInfoRow(Icons.Default.Phone, "Teléfono", datos.telefono ?: "") { /*no-edit*/ }
                    EditableInfoRow(Icons.Default.Email, "Email", datos.email) { /*no-edit*/ }
                    EditableInfoRow(Icons.Default.Home, "Dirección", datos.direccion ?: "") { /*no-edit*/ }
                    Spacer(modifier = Modifier.height(24.dp))
                }

                item {
                    SectionTitle("Datos Personales")
                    EditableInfoRow(Icons.Default.Badge, "DNI", datos.dni ?: "") { /*no-edit*/ }
                    EditableInfoRow(Icons.Default.Cake, "Fecha de Nacimiento", datos.fechaNacimiento ?: "") { /*no-edit*/ }
                    EditableInfoRow(Icons.Default.Security, "Nº Seguridad Social", datos.numeroSeguridadSocial ?: "") { /*no-edit*/ }
                    EditableInfoRow(Icons.Default.AccountBalance, "IBAN", datos.iban ?: "") { /*no-edit*/ }
                    Spacer(modifier = Modifier.height(24.dp))
                }

                item {
                    AcademicSection(
                        initialFormacion = datos.formacionAcademica ?: "",
                        onFormacionChange = { nueva ->

                        }
                    )
                }

                item {
                    SobreMiSection(
                        initialSobreMi = datos.datosPersonales ?: "",
                        onSobreMiChange = { nueva ->
                            // aquí podrías llamar a vm.updateDatosPersonales(nueva)
                        }
                    )
                }
                item {
                    Spacer(Modifier.height(24.dp))
                    // Botón Cerrar sesión
                    Button(
                        onClick = {
                            // limpiamos SharedPreferences
                            context
                                .getSharedPreferences("UsuarioPrefs", Context.MODE_PRIVATE)
                                .edit()
                                .clear()
                                .apply()
                            navController.navigate("pantalla_iniciar_sesion")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF81D4FA),
                            contentColor   = Color.White
                        )
                    ) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Cerrar sesión")
                        Spacer(Modifier.width(8.dp))
                        Text("Cerrar sesión")
                    }
                    Spacer(Modifier.height(24.dp))
                }
            }
        }
    }
}

@Composable
fun SobreMiSection(
    initialSobreMi: String,
    onSobreMiChange: (String) -> Unit
) {
    SectionTitle("Sobre mí")

    var sobreMi by remember { mutableStateOf(initialSobreMi) }
    var isEditing by remember { mutableStateOf(initialSobreMi.isBlank()) }

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        if (isEditing) {
            OutlinedTextField(
                value = sobreMi,
                onValueChange = {
                    sobreMi = it
                    onSobreMiChange(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 120.dp),
                placeholder = { Text("Escribe algo sobre ti") },
                singleLine = false,
                maxLines = 5
            )
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { isEditing = false }) {
                    Icon(Icons.Default.Check, contentDescription = "Guardar")
                }
            }
        } else {
            Text(
                text = if (sobreMi.isBlank()) "Sin descripción" else sobreMi,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
            IconButton(onClick = { isEditing = true }) {
                Icon(Icons.Default.Edit, contentDescription = "Editar")
            }
        }
    }
    Spacer(Modifier.height(24.dp))
}
@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
            .padding(vertical = 8.dp, horizontal = 16.dp)
    )
}

@Composable
private fun EditableInfoRow(
    icon: ImageVector,
    label: String,
    value: String?,
    onValueChange: (String) -> Unit
) {
    var isEditing by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf(value ?: "") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Text("$label:", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        Spacer(modifier = Modifier.width(8.dp))

        if (isEditing) {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.weight(1f),
                singleLine = true
            )
            Spacer(modifier = Modifier.width(4.dp))
            IconButton(onClick = {
                isEditing = false
                onValueChange(text) // Guardar valor
            }) {
                Icon(Icons.Default.Check, contentDescription = "Guardar")
            }
        } else {
            Text(
                text = if (text.isBlank()) "Sin información" else text,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f),
                overflow = TextOverflow.Ellipsis
            )
            IconButton(onClick = { isEditing = true }) {
                Icon(Icons.Default.Edit, contentDescription = "Editar")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProfileScreen(){
    ProyectoBussinesOneTheme {
        ProfileScreen(usuarioId = 2L, navController = rememberNavController())
    }
}