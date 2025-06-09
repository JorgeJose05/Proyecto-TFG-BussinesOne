package com.example.proyectobussinesone.ComponenteKotlinPrueba

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import java.time.LocalDate

import androidx.compose.material3.*
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import java.time.YearMonth
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.proyectobussinesone.MainActivity
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime


@Composable
fun DetalleDiaScreen(
    fecha: LocalDate,
    onBack: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var inputInTime by remember { mutableStateOf("") }
    var inputOutTime by remember { mutableStateOf("") }
    // ViewModel
    val context = LocalContext.current

    val viewModel: TimeTrackerViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                TimeTrackerViewModel(context) as T
        }
    )

    //  Carga entradas del mes en el VM
    LaunchedEffect(fecha.withDayOfMonth(1)) {
        viewModel.loadMonthEntries(YearMonth.from(fecha))
    }
    //  Obtener entradas del día
    val allEntries by viewModel.entries.collectAsStateWithLifecycle(initialValue = emptyList())
    val dayEntries = remember(fecha, allEntries) {
        allEntries.filter { it.date == fecha }
    }

    // Simula las horas trabajadas (por ejemplo horas 9 a 17)
    //  Calcular horas trabajadas por hora
    val horasTrabajadas = remember(dayEntries) {
        dayEntries.flatMap { entry ->
            val startH = entry.inTime.hour
            val endH   = entry.outTime?.hour ?: LocalTime.now().hour
            (startH..(endH - 1)).toList()
        }.toSet()
    }
    val totalSeconds = dayEntries.sumOf { entry ->
        val end = entry.outTime ?: LocalTime.now()
        Duration.between(entry.inTime, end).seconds
    }
    var isClockedIn by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Barra superior con fecha y botón Atrás
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
            }
            Spacer(Modifier.width(8.dp))
            Text(fecha.toString(), style = MaterialTheme.typography.titleLarge)
        }

        Spacer(Modifier.height(24.dp))


        // Progreso total (barra y botón fichar)
        DetalleHorasDelDia(
           // dayEntries      = dayEntries,
            horasTrabajadas = horasTrabajadas,
            isToday         = fecha == LocalDate.now(),
            isClockedIn     = viewModel.isClockedIn.value,
            onToggleClock   = { viewModel.toggleClock() },
            onShowDialog    = { showDialog = true }
        )

        Spacer(Modifier.height(24.dp))

        // Diálogo de ajuste manual
        if (showDialog) {
            AjusteDialog(
                inputInTime = inputInTime,
                onInTimeChange = { inputInTime = it },
                viewModel = viewModel,
                fecha           = fecha,
                inputOutTime = inputOutTime,
                onOutTimeChange = { inputOutTime = it },
                onSave = { inT, outT ->

                    viewModel.addTimeEntry(
                        TimeEntry(fecha, inT, outT, isRealTime = false)
                    )
                    showDialog = false
                },
                onCancel = { showDialog = false }
            )
        }

        Spacer(Modifier.height(24.dp))

        Text(
            text = "Fichajes de ${fecha.dayOfMonth}/${fecha.monthValue}/${fecha.year}",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
        )
        EntriesListForDay(day = fecha, viewModel = viewModel)
    }
}
@Composable
private fun DetalleHorasDelDia(
    //dayEntries: List<TimeEntry>,
    horasTrabajadas: Set<Int>,
    isToday: Boolean,
    isClockedIn: Boolean,
    onToggleClock: () -> Unit,
    onShowDialog: () -> Unit
) {

    // Visualizamos 24 bloques
    Row(modifier = Modifier.fillMaxWidth()) {
        (0 until 24).forEach { hora ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp)
                    .background(
                        if (hora in horasTrabajadas) Color(0xFF2196F3)
                        else MaterialTheme.colorScheme.surfaceVariant
                    )
            )
            if (hora < 23) Spacer(Modifier.width(1.dp))
        }
    }
    Spacer(Modifier.height(8.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("00"); Text("06"); Text("12"); Text("18"); Text("23")
    }

    Spacer(Modifier.height(16.dp))

    // Botones de fichar y solicitar ajuste
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            onClick = onToggleClock,
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2196F3),  // tu azul
                contentColor   = Color.White         // color del texto
            ),
            enabled = isToday,
        ) {
            Text(if (isClockedIn) "Desfichar" else "Fichar" )
        }
        OutlinedButton(
            onClick = onShowDialog,
            modifier = Modifier.weight(1f),
            enabled = true
        ) {
            Text("Solicitar fichaje",
                color = Color(0xFF2196F3) )
        }
    }
}

@Composable
private fun AjusteDialog(
    viewModel: TimeTrackerViewModel, // <-- AÑADIR ESTO
    fecha: LocalDate,                // <-- AÑADIR ESTO
    inputInTime: String,
    onInTimeChange: (String) -> Unit,
    inputOutTime: String,
    onOutTimeChange: (String) -> Unit,
    onSave: (LocalTime, LocalTime?) -> Unit,
    onCancel: () -> Unit
) {

    val userId = MainActivity.UsuarioSesion.getUserId(LocalContext.current)
    fun normalize(input: String): String {
        val trimmed = input.trim()
        return when {
            trimmed.isBlank() -> ""
            ':' in trimmed    -> trimmed
            trimmed.length in 1..2 -> {
                // permitimos 0–24
                val h = trimmed.toIntOrNull()?.coerceIn(0, 24) ?: 0
                // si es 24 lo normalizamos a “00:00” (medianoche)
                if (h == 24) "00:00" else "%02d:00".format(h)
            }
            else -> trimmed
        }
    }


    AlertDialog(
        onDismissRequest = onCancel,
        confirmButton = {
            TextButton(onClick = {
                // Normalizamos antes de parsear
                val rawIn  = normalize(inputInTime)
                val rawOut = normalize(inputOutTime)

                // Parse seguro con fallback
                val inT = try {
                    if (rawIn.isNotBlank()) LocalTime.parse(rawIn)
                    else LocalTime.MIDNIGHT
                } catch (e: Exception) {
                    LocalTime.now()
                }
                val outT = try {
                    rawOut.takeIf { it.isNotBlank() }?.let { if (it == "00:00" && inputOutTime.trim().toIntOrNull() == 24)
                        LocalTime.of(23, 59)
                    else
                        LocalTime.parse(it) }
                } catch (e: Exception) {
                    null
                }

                val empleadoId = userId // <-- reemplaza por el ID real del empleado

                viewModel.crearFichaje(
                    empleadoId  = empleadoId,
                    fecha       = fecha,
                    horaEntrada = inT,
                    horaSalida  = outT,
                    tiempoReal  = false // porque es ajuste manual
                ) { exito ->
                    if (!exito) {
                        Log.e("DetalleDiaScreen", "❌ Error al crear fichaje")
                    }
                }

                onSave(inT, outT)
            }) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text("Cancelar")
            }
        },
        title = { Text("Ajuste de fichaje") },
        text = {

            Column {
                Text("Introduce solo la hora de entrada y salida.")
                Spacer(Modifier.height(8.dp))
                TextField(
                    value = inputInTime,
                    onValueChange = onInTimeChange,
                    label = { Text("Hora Entrada (HH)") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Spacer(Modifier.height(8.dp))
                TextField(
                    value = inputOutTime,
                    onValueChange = onOutTimeChange,
                    label = { Text("Hora Salida (HH)") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        }
    )
}
@Composable
fun EntriesListForDay(day: LocalDate, viewModel: TimeTrackerViewModel) {
    val allEntries by viewModel.entries.collectAsStateWithLifecycle(initialValue = emptyList())
    val entries = remember(day, allEntries) { allEntries.filter { it.date == day } }
    if (entries.isEmpty()) {
        Text("No hay fichajes este día.", modifier = Modifier.padding(16.dp))
    } else {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(entries) { entry ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(entry.inTime.toString())
                    Text(entry.outTime?.toString() ?: "—")
                }
                Divider()
            }
        }
    }
}
