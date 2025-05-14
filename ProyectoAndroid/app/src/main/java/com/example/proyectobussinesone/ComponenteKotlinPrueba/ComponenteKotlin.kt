package com.example.proyectobussinesone.ComponenteKotlinPrueba

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.proyectobussinesone.ui.theme.ProyectoBussinesOneTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.filled.Timer
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import java.time.Duration
import java.time.LocalDateTime

@Composable
fun CustomCalendar(
    viewModel: TimeTrackerViewModel,
    currentMonth: YearMonth,
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    onNextMonth: () -> Unit,
    onPreviousMonth: () -> Unit,
    secondsWorked: Long,   // ahora en segundos
    maxHours: Float
) {
    val days = remember(currentMonth) {
        generateMonthDays(currentMonth)
    }

    Column {
        // Fila con los botones para cambiar de mes
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = onPreviousMonth) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Mes anterior")
            }
            Text(
                text = currentMonth.month.name + " " + currentMonth.year,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            IconButton(onClick = onNextMonth) {
                Icon(imageVector = Icons.Filled.ArrowForward, contentDescription = "Mes siguiente")
            }
        }

        WeekDaysHeader()
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(days.size) { index ->
                val date = days[index]
                DayCell(
                    date = date,
                    isSelected = date == selectedDate,
                    onClick = { onDateSelected(date) }
                )
            }
        }
        // Barra de progreso de horas trabajadas
        ProgressBar(
            secondsWorked = secondsWorked,
            maxHours       = maxHours
        )
        // Botón de fichar/desfichar
        ClockInOutButton(
            isClockedIn = viewModel.isClockedIn.value,
            clockInTime = viewModel.clockInTime,
            onToggleClock = { viewModel.toggleClock() }
        )
    }
}

class TimeTrackerViewModel : ViewModel() {

    // Hora en que se fichó
    private val _clockInTime = mutableStateOf<LocalDateTime?>(null)
    val clockInTime: LocalDateTime? get() = _clockInTime.value

    // Segundos trabajados acumulados en la semana
    private val _secondsWorked = mutableStateOf(0L)
    val secondsWorked: State<Long> = _secondsWorked

    // Estado de fichado
    private val _isClockedIn = mutableStateOf(false)
    val isClockedIn: State<Boolean> = _isClockedIn

    fun toggleClock() {
        if (_isClockedIn.value) {
            // — DESFICHAR —
            val checkOutTime = LocalDateTime.now()
            _clockInTime.value?.let { start ->
                val duration = Duration.between(start, checkOutTime)
                _secondsWorked.value += duration.seconds
            }
            _clockInTime.value = null
            _isClockedIn.value = false
        } else {
            // — FICHAR —
            _clockInTime.value = LocalDateTime.now()
            _isClockedIn.value = true
        }
    }
}

@Composable
fun CalendarioConFichaje(viewModel: TimeTrackerViewModel = TimeTrackerViewModel()) {
    val selectedDate = remember { mutableStateOf<LocalDate?>(null) }
    val currentMonth = remember { mutableStateOf(YearMonth.now()) }

    Column {

        CustomCalendar(
            viewModel       = viewModel,
            currentMonth    = currentMonth.value,
            selectedDate    = selectedDate.value,
            onDateSelected  = { selectedDate.value = it },
            onNextMonth     = { currentMonth.value = currentMonth.value.plusMonths(1) },
            onPreviousMonth = { currentMonth.value = currentMonth.value.minusMonths(1) },
            secondsWorked   = viewModel.secondsWorked.value,
            maxHours        = 40f // semana de 40h
        )
    }
}


@Composable
fun ProgressBar(secondsWorked: Long, maxHours: Float) {
    val maxMinutes = (maxHours * 60).toLong()
    val totalMinutes = secondsWorked / 60
    val progress = (totalMinutes.toFloat() / maxMinutes).coerceIn(0f, 1f)

    val hours   = totalMinutes / 60
    val minutes = totalMinutes % 60

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(36.dp)
                .background(
                    color = Color(0xFFE0E0E0),
                    shape = RoundedCornerShape(50)
                )
                .padding(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(progress)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(Color(0xFF2196F3), Color(0xFF0D47A1))
                        ),
                        shape = RoundedCornerShape(50)
                    )
            )

            Text(
                text = "${hours}h ${minutes}m / ${maxHours.toInt()}h",
                style = MaterialTheme.typography.subtitle2.copy(
                    color      = Color.White,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun ClockInOutButton(
    isClockedIn: Boolean,
    clockInTime: LocalDateTime?, // Hora en que se fichó
    onToggleClock: () -> Unit
) {
    val buttonColor = if (isClockedIn) Color(0xFFE53935) else Color(0xFF43A047)
    val buttonIcon = Icons.Default.Timer

    // Calculamos el tiempo transcurrido si está fichado
    val elapsed by produceState(initialValue = Duration.ZERO, key1 = clockInTime, key2 = isClockedIn) {
        while (isClockedIn && clockInTime != null) {
            value = Duration.between(clockInTime, LocalDateTime.now())
            delay(1000L)
        }
    }

    val formattedTime = String.format(
        "%02dh %02dm %02ds",
        elapsed.toHours(),
        elapsed.toMinutes() % 60,
        elapsed.seconds % 60
    )

    val buttonText = if (isClockedIn) "Desfichar ($formattedTime)" else "Fichar"

    Button(
        onClick = onToggleClock,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp)
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = buttonColor),
        shape = RoundedCornerShape(30.dp),
        elevation = ButtonDefaults.elevation(6.dp)
    ) {
        Icon(
            imageVector = buttonIcon,
            contentDescription = null,
            tint = Color.White
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = buttonText,
            style = MaterialTheme.typography.button.copy(
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}


@Composable
fun DayCell(
    date: LocalDate,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val today = LocalDate.now()
    val isToday = date == today
    val backgroundColor = when {
        isSelected -> Color.Blue
        isToday -> Color.LightGray
        else -> Color.Transparent
    }

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .background(backgroundColor)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(text = date.dayOfMonth.toString())
    }
}

@Composable
fun WeekDaysHeader() {
    val daysOfWeek = listOf("Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom")
    Row(modifier = Modifier.fillMaxWidth()) {
        daysOfWeek.forEach { day ->
            Text(
                text = day,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }
    }
}

fun generateMonthDays(month: YearMonth): List<LocalDate> {
    val firstDayOfMonth = month.atDay(1)
    val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value

    // Calcular el desplazamiento para obtener el primer lunes del mes
    val startDate = if (firstDayOfWeek == 1) {
        firstDayOfMonth
    } else {
        firstDayOfMonth.minusDays((firstDayOfWeek - 1).toLong())
    }

    return (0 until 42).map { startDate.plusDays(it.toLong()) }
}


@Preview(showBackground = true)
@Composable
fun PreviewPantallaDetalle() {
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
    val viewModel = remember { TimeTrackerViewModel() }

    CalendarioConFichaje(viewModel)
    ProyectoBussinesOneTheme {
        CalendarioConFichaje(viewModel)
    }
}

class CalendarViewModel : ViewModel() {
    private val _currentDate = MutableStateFlow(LocalDate.now())
    val currentDate: StateFlow<LocalDate> = _currentDate

    init {
        viewModelScope.launch {
            while (true) {
                delay(60_000) // Actualiza cada minuto
                _currentDate.value = LocalDate.now()
            }
        }
    }
}
/*
@Preview(showBackground = true)
@Composable
fun PreviewProgresBar(){
    ProyectoBussinesOneTheme {
        ProgressBar(15f,40f)
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewClickOnButton(){

    // Estado de si está fichado o no
    var isClockedIn by remember { mutableStateOf(false) }
    // Lógica para fichar/desfichar
    var hoursWorked by remember { mutableStateOf(15f) }
    val onToggleClock: () -> Unit = {
        isClockedIn = !isClockedIn
        if (isClockedIn) {
            hoursWorked += 1f  // Si ficha, agrega una hora (puedes personalizar esta lógica)
        }
    }
    ProyectoBussinesOneTheme {
        ClockInOutButton(true, onToggleClock)
    }
}
*/