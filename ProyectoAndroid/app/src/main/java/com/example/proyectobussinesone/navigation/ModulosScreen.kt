package com.example.proyectobussinesone.navigation

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
import androidx.compose.ui.text.font.FontWeight
import com.example.proyectobussinesone.ComponenteProductos.ComponentePrueba
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.fragment.compose.AndroidFragment
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.proyectobussinesone.ComponenteKotlinPrueba.CalendarioConFichaje
import com.example.proyectobussinesone.ComponenteKotlinPrueba.DetalleDiaScreen
import com.example.proyectobussinesone.ComponenteProductos.Menu
import com.example.proyectobussinesone.ModuloViewStateB
import com.example.proyectobussinesone.ModuloViewModel
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
                    label    = { Text("Punto de venta") },
                    icon     = { Icon(Icons.Default.ShoppingCart, contentDescription = null) },
                    selected = false,
                    onClick  = {
                        scope.launch { drawerState.close() }
                        navController.navigate("modulo2") }
                )
                NavigationDrawerItem(
                    label    = { Text("Fichaje") },
                    icon     = { Icon(Icons.Default.Receipt, contentDescription = null) },
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
                    Modulo3Screen(navController)
                }
                composable(
                    "detalle/{fecha}",
                    arguments = listOf(navArgument("fecha") {
                        type = NavType.StringType
                    })
                ) { backStack ->
                    val fechaStr = backStack.arguments?.getString("fecha")!!
                    val fecha = LocalDate.parse(fechaStr)
                    DetalleDiaScreen(
                        fecha = fecha,
                        onBack = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}

// Definimos las pantallas

@Composable
fun TiendaScreen(  vm: ModuloViewModel = viewModel() ) {

    // 4.1 Recogemos el estado
    val uiState = vm.uiState.collectAsState()

    // 4.2 Estado de búsqueda
    var query by rememberSaveable { mutableStateOf("") }

    // 4.3 Listado filtrado (inicialmente vacío hasta que llegue Success)
    val modulosDisponibles = remember(query, uiState.value) {
        when (val state = uiState.value) {
            is ModuloViewStateB.Loading -> emptyList<ModuloStoreItem>()
            is ModuloViewStateB.Error   -> emptyList()
            is ModuloViewStateB.Success -> {
                // Mapeamos cada ModuloDto a ModuloStoreItem
                state.lista.map { dto ->
                    ModuloStoreItem(
                        icon = iconoDesdeNombre(dto.icono),
                        categoria = dto.grupo,
                        nombre = dto.nombre,
                        descripcion = "Aquí va la descripción real"
                    )
                }
                    // Después filtramos por query:
                    .filter {
                        it.nombre.contains(query, ignoreCase = true)
                                || it.categoria.contains(query, ignoreCase = true)
                                || it.descripcion.contains(query, ignoreCase = true)
                    }
            }
        }
    }


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

fun iconoDesdeNombre(nombreIcono: String): ImageVector {
    return when (nombreIcono) {
        "Inventario"      -> Icons.Default.Inventory
        "Clientes"         -> Icons.Default.Person
        "Facturación"        -> Icons.Default.Receipt
        "Ventas"      -> Icons.Default.Analytics
        "Proyectos"          -> Icons.Default.Build
        "Contabilidad" -> Icons.Default.AccountBalance
        "Soporte"   -> Icons.Default.SupportAgent
        "Recursos Humanos"          -> Icons.Default.Group
        "Marketing"       -> Icons.Default.Campaign
        "eCommerce"   -> Icons.Default.ShoppingCart
        "Administración" -> Icons.Default.Accessibility
        "Catálogo" -> Icons.Default.MenuBook
        "Informes" -> Icons.Default.BarChart
        "Reportes" -> Icons.Default.Description

        else             -> Icons.Default.HelpOutline
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
        clazz = Menu::class.java
    )
}

@Composable
fun Modulo3Screen(navController: NavHostController) {
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

    CalendarioConFichaje( onDateClick = { date ->
        navController.navigate("detalle/${date}")
    },)
}

@Preview(showBackground = true)
@Composable
fun PreviewModulosScreen() {
    ModulosScreen()
}