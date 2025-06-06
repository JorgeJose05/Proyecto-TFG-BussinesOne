package com.example.proyectobussinesone.ComponenteKotlinPrueba



import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectobussinesone.ComponenteKotlinPrueba.models.Fichaje
import com.example.proyectobussinesone.ComponenteKotlinPrueba.models.FichajePostRequestDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.YearMonth



import android.util.Log
import com.example.proyectobussinesone.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FichajeViewModel : ViewModel() {

    companion object {
        private const val TAG = "FichajeViewModel"
    }

    private val _uiState = MutableStateFlow<FichajeViewState>(FichajeViewState.Loading)
    val uiState: StateFlow<FichajeViewState> = _uiState

    init {
        cargarFichajes()
    }

    private fun cargarFichajes() {
        viewModelScope.launch {
            _uiState.value = FichajeViewState.Loading
            Log.d(TAG, "📡 Iniciando petición a /Fichaje/GET…")
            try {
                val response = RetrofitClient.moduloApiService.obtenerTodosLosFichajes()
                Log.d(TAG, "🏷 Código HTTP recibido: ${response.code()}")
                if (response.isSuccessful) {
                    val lista: List<Fichaje> = response.body() ?: emptyList()
                    Log.d(TAG, " Body de la respuesta: $lista")
                    _uiState.value = FichajeViewState.Success(lista)
                } else {
                    Log.e(TAG, "️ Error HTTP: ${response.code()} - ${response.message()}")
                    _uiState.value = FichajeViewState.Error("Error ${response.code()}: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e(TAG, " Excepción en Retrofit: ${e.localizedMessage}", e)
                _uiState.value = FichajeViewState.Error("Excepción: ${e.localizedMessage}")
            }
        }
    }





}