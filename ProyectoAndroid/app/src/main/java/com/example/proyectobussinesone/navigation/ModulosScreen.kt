package com.example.proyectobussinesone.navigation

import android.view.View
import com.example.proyectobussinesone.ModuloStoreItem
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Apps
//import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.*
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.navigation.findNavController
import com.example.proyectobussinesone.R
import com.example.proyectobussinesone.ComponentePrueba
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.fragment.compose.AndroidFragment
import com.example.proyectobussinesone.ComponenteKotlinPrueba.CalendarioConFichaje
import com.example.proyectobussinesone.ComponenteKotlinPrueba.CustomCalendar
import com.example.proyectobussinesone.ComponenteKotlinPrueba.TimeTrackerViewModel
import java.time.LocalDate
import java.time.YearMonth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModulosScreen() {
    // Estado del drawer y coroutine scope
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope       = rememberCoroutineScope()
    val navController = rememberNavController()

    ModalNavigationDrawer(
        drawerState   = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    text     = "Menú",
                    modifier = Modifier.padding(16.dp),
                    style    = MaterialTheme.typography.titleMedium
                )
                Divider()
                NavigationDrawerItem(
                    label    = { Text("Tienda de modulos") },
                    icon     = { Icon(Icons.Default.Apps, contentDescription = null) },
                    selected = false,
                    onClick  = {
                        scope.launch { drawerState.close() }
                        navController.navigate("tienda") }
                )
                Divider()
                NavigationDrawerItem(
                    label    = { Text("modulo1") },
                    icon     = { Icon(Icons.Default.Apps, contentDescription = null) },
                    selected = false,
                    onClick  = {
                        scope.launch { drawerState.close() }
                        navController.navigate("modulo1") }
                )
                NavigationDrawerItem(
                    label    = { Text("modulo2") },
                    icon     = { Icon(Icons.Default.Apps, contentDescription = null) },
                    selected = false,
                    onClick  = {
                        scope.launch { drawerState.close() }
                        navController.navigate("modulo2") }
                )
                NavigationDrawerItem(
                    label    = { Text("modulo3") },
                    icon     = { Icon(Icons.Default.Apps, contentDescription = null) },
                    selected = false,
                    onClick  = {
                        scope.launch { drawerState.close() }
                        navController.navigate("modulo3") }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Pantalla de Módulos") },
                    navigationIcon = {
                        IconButton(
                            onClick = { scope.launch { drawerState.open() } }
                        ) {
                            Icon(Icons.Default.Menu, contentDescription = "Abrir menú", modifier = Modifier.size(40.dp))
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor    = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "tienda",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("tienda") {
                    TiendaScreen()
                }
                composable("modulo1") {
                    Modulo1Screen()
                }
                composable("modulo2") {
                    Modulo2Screen()
                }
                composable("modulo3") {
                    Modulo3Screen()
                }
            }
        }
    }
}

// Definimos las pantallas

@Composable
fun TiendaScreen() {
    // Lista de productos simulada
    val modulosDisponibles = listOf(
        ModuloStoreItem(
            icon = Icons.Default.Inventory,
            categoria = "Inventario",
            nombre = "Gestión de Inventario",
            descripcion = "Controla y gestiona tu stock de productos de manera eficiente."
        ),
        ModuloStoreItem(
            icon = Icons.Default.Person,
            categoria = "Clientes",
            nombre = "CRM de Clientes",
            descripcion = "Administra las relaciones con tus clientes y mejora la fidelización."
        ),
        ModuloStoreItem(
            icon = Icons.Default.Receipt,
            categoria = "Facturación",
            nombre = "Facturación Electrónica",
            descripcion = "Genera y envía facturas electrónicas cumpliendo con la normativa vigente."
        ),
        ModuloStoreItem(
            icon = Icons.Default.Analytics,
            categoria = "Ventas",
            nombre = "Análisis de Ventas",
            descripcion = "Obtén informes detallados sobre el rendimiento de tus ventas."
        ),
        ModuloStoreItem(
            icon = Icons.Default.Build,
            categoria = "Proyectos",
            nombre = "Gestión de Proyectos",
            descripcion = "Planifica y supervisa tus proyectos desde una única plataforma."
        ),
        ModuloStoreItem(
            icon = Icons.Default.AccountBalance,
            categoria = "Contabilidad",
            nombre = "Contabilidad Avanzada",
            descripcion = "Lleva un control preciso de tus finanzas y contabilidad."
        ),
        ModuloStoreItem(
            icon = Icons.Default.SupportAgent,
            categoria = "Soporte",
            nombre = "Soporte Técnico",
            descripcion = "Gestiona las solicitudes de soporte y mejora la atención al cliente."
        ),
        ModuloStoreItem(
            icon = Icons.Default.Group,
            categoria = "Recursos Humanos",
            nombre = "Gestión de Recursos Humanos",
            descripcion = "Administra la información y procesos relacionados con tu personal."
        ),
        ModuloStoreItem(
            icon = Icons.Default.Campaign,
            categoria = "Marketing",
            nombre = "Módulo de Marketing",
            descripcion = "Diseña y ejecuta campañas de marketing efectivas."
        ),
        ModuloStoreItem(
            icon = Icons.Default.ShoppingCart,
            categoria = "eCommerce",
            nombre = "Integración con eCommerce",
            descripcion = "Conecta tu tienda en línea y sincroniza automáticamente los datos."
        )
    )

    var query by rememberSaveable { mutableStateOf("") }

    // Filtrar productos según la consulta
    val modulosFiltrados = modulosDisponibles.filter {
        it.nombre.contains(query, ignoreCase = true) ||
        it.categoria.contains(query, ignoreCase = true) ||
        it.descripcion.contains(query, ignoreCase = true) //Puede que quite el filtro por descripcion porque puede ser muy larga y contener palabras que confundan el filtro
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        // Barra de búsqueda
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Buscar módulos") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        // Lista de productos
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(modulosFiltrados) { modulo ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { /* Acción al hacer clic en el producto */ },
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = modulo.icon,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column { Text(
                            text = modulo.nombre,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                            Text(
                                text = modulo.categoria,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary
                            ) }

                    }
                }
            }
        }
    }
}

@Composable
fun Modulo1Screen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFAAF683)),
        contentAlignment = Alignment.Center
    ) {
        Text("🏠 Inicio", color = Color.Black)
    }
}

@Composable
fun Modulo2Screen() {
    AndroidFragment(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFD6A5)),
        clazz = com.example.proyectobussinesone.ComponentePrueba::class.java
    )
}

@Composable
fun Modulo3Screen() {
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

    var currentMonth by remember { mutableStateOf(YearMonth.now()) }

    // Estado para las horas trabajadas y el máximo de horas
    var hoursWorked by remember { mutableStateOf(15f) }
    val maxHours = 40f

    // Estado de si está fichado o no
    var isClockedIn by remember { mutableStateOf(false) }

    val onDateSelected: (LocalDate) -> Unit = { date ->
        selectedDate = date
    }

    // Lógica para mover el mes hacia adelante y hacia atrás
    val onNextMonth: () -> Unit = {
        currentMonth = currentMonth.plusMonths(1)
    }

    val onPreviousMonth: () -> Unit = {
        currentMonth = currentMonth.minusMonths(1)
    }

    // Lógica para fichar/desfichar
    val onToggleClock: () -> Unit = {
        isClockedIn = !isClockedIn
        if (isClockedIn) {
            hoursWorked += 1f  // Si ficha, agrega una hora (puedes personalizar esta lógica)
        }
    }

    CalendarioConFichaje()
}

@Preview(showBackground = true)
@Composable
fun PreviewModulosScreen() {
    ModulosScreen()
}