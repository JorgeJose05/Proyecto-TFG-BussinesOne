package com.example.proyectobussinesone.ComponenteKotlinPrueba

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Timer
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.*
import java.time.format.TextStyle
import java.util.*
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.clickable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.proyectobussinesone.ui.theme.ProyectoBussinesOneTheme


private val Context.dataStore by preferencesDataStore("time_prefs")

class TimeTrackerViewModel(private val context: Context) : ViewModel() {

    companion object {
        private val CLOCK_IN_KEY = longPreferencesKey("clock_in_ts")
    }

    private val _clockInTime = mutableStateOf<LocalDateTime?>(null)
    val clockInTime: LocalDateTime? get() = _clockInTime.value

    private val _secondsWorked = mutableStateOf(0L)
    val secondsWorked: State<Long> = _secondsWorked

    private val _isClockedIn = mutableStateOf(false)
    val isClockedIn: State<Boolean> = _isClockedIn

    init {
        viewModelScope.launch {
            val ts = context.dataStore.data
                .map { it[CLOCK_IN_KEY] ?: -1L }
                .first()
            if (ts > 0) {
                _clockInTime.value = LocalDateTime.ofEpochSecond(ts, 0, ZoneOffset.UTC)
                _isClockedIn.value = true
            }
        }
    }

    fun toggleClock() {
        viewModelScope.launch {
            if (_isClockedIn.value) {
                val now = LocalDateTime.now()
                _clockInTime.value?.let { start ->
                    _secondsWorked.value += Duration.between(start, now).seconds
                }
                _clockInTime.value = null
                _isClockedIn.value = false
                context.dataStore.edit { it.remove(CLOCK_IN_KEY) }
            } else {
                val now = LocalDateTime.now()
                _clockInTime.value = now
                _isClockedIn.value = true
                context.dataStore.edit { it[CLOCK_IN_KEY] = now.toEpochSecond(ZoneOffset.UTC) }
            }
        }
    }
}

@Composable
fun CalendarioConFichaje() {
    val context = LocalContext.current
    val vm: TimeTrackerViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                TimeTrackerViewModel(context) as T
        }
    )

    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }

    CustomCalendar(
        viewModel       = vm,
        currentMonth    = currentMonth,
        selectedDate    = selectedDate,
        onDateSelected  = { selectedDate = it },
        onPreviousMonth = { currentMonth = currentMonth.minusMonths(1) },
        onNextMonth     = { currentMonth = currentMonth.plusMonths(1) },
        secondsWorked   = vm.secondsWorked.value,
        maxHours        = 40f
    )
}

@Composable
fun CustomCalendar(
    viewModel: TimeTrackerViewModel,
    currentMonth: YearMonth,
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit,
    secondsWorked: Long,
    maxHours: Float
) {
    val days = remember(currentMonth) { generateMonthDays(currentMonth) }

    Column {
        // Navegación de mes
        Row(
            Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = onPreviousMonth) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Anterior")
            }
            Text(
                text = currentMonth.month.getDisplayName(TextStyle.FULL, Locale("es"))
                    .replaceFirstChar { it.uppercase() } + " ${currentMonth.year}",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            IconButton(onClick = onNextMonth) {
                Icon(Icons.Filled.ArrowForward, contentDescription = "Siguiente")
            }
        }

        WeekDaysHeader()
        LazyVerticalGrid(columns = GridCells.Fixed(7), modifier = Modifier.fillMaxWidth()) {
            items(days) { date ->
                DayCell(date, date == selectedDate) { onDateSelected(date) }
            }
        }

        ProgressBar(secondsWorked, maxHours)
        ClockInOutButton(viewModel.isClockedIn.value, viewModel.clockInTime) { viewModel.toggleClock() }
    }
}
@Composable
fun ProgressBar(secondsWorked: Long, maxHours: Float) {
    val totalMin = secondsWorked / 60
    val maxMin   = (maxHours * 60).toLong()
    val progress = (totalMin.toFloat() / maxMin).coerceIn(0f, 1f)
    val h        = totalMin / 60
    val m        = totalMin % 60

    Box(Modifier.fillMaxWidth().padding(16.dp)) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(36.dp)
                .background(Color(0xFFE0E0E0), RoundedCornerShape(50))
                .padding(4.dp)
        ) {
            Box(
                Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(progress)
                    .background(
                        Brush.horizontalGradient(
                            listOf(Color(0xFF2196F3), Color(0xFF0D47A1))
                        ),
                        RoundedCornerShape(50)
                    )
            )
            Text(
                "$h h $m m / ${maxHours.toInt()}h",
                Modifier.align(Alignment.Center),
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// --------------------------------------
// CLOCK IN/OUT BUTTON
// --------------------------------------
@Composable
fun ClockInOutButton(
    isClockedIn: Boolean,
    clockInTime: LocalDateTime?,
    onToggleClock: () -> Unit
) {
    val color = if (isClockedIn) Color(0xFFE53935) else Color(0xFF43A047)
    val elapsed by produceState(initialValue = Duration.ZERO, isClockedIn, clockInTime) {
        while (isClockedIn && clockInTime != null) {
            value = Duration.between(clockInTime, LocalDateTime.now())
            delay(1000L)
        }
    }
    val s = elapsed.seconds
    val text = if (isClockedIn)
        String.format("Desfichar (%02dh:%02dm)", s/3600, (s%3600)/60)
    else "Fichar"

    Button(
        onClick = onToggleClock,
        Modifier.fillMaxWidth().padding(16.dp).height(56.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = color),
        shape = RoundedCornerShape(30.dp),
        elevation = ButtonDefaults.elevation(6.dp)
    ) {
        Icon(Icons.Default.Timer, contentDescription = null, tint = Color.White)
        Spacer(Modifier.width(8.dp))
        Text(text, color = Color.White)
    }
}

@Composable
fun DayCell(date: LocalDate, isSelected: Boolean, onClick: () -> Unit) {
    val background = when {
        isSelected -> Color.Blue
        date == LocalDate.now() -> Color.LightGray
        else -> Color.Transparent
    }
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .background(background)
            .clickable { onClick() },  // <-- Lambda aquí
        contentAlignment = Alignment.Center
    ) {
        Text(date.dayOfMonth.toString())
    }
}

@Composable
fun WeekDaysHeader() {
    Row(Modifier.fillMaxWidth()) {
        listOf("Lun","Mar","Mié","Jue","Vie","Sáb","Dom").forEach {
            Text(it, Modifier.weight(1f), textAlign = TextAlign.Center)
        }
    }
}

fun generateMonthDays(month: YearMonth): List<LocalDate> {
    val first = month.atDay(1)
    val dow = first.dayOfWeek.value // Mon=1..Sun=7
    val start = first.minusDays((dow-1).toLong())
    return (0 until 42).map { start.plusDays(it.toLong()) }
}

@Preview(showBackground = true)
@Composable
fun PreviewCalendarioConFichaje() {
    ProyectoBussinesOneTheme {
        // Creamos un NavController de prueba para el Preview
        val navController = rememberNavController()
        CalendarioConFichaje()
    }
}