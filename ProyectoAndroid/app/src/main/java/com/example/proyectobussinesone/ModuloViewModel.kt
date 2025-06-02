package com.example.proyectobussinesone

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class ModuloViewStateB {
    object Loading : ModuloViewStateB()
    data class Success(val lista: List<ModuloDto>) : ModuloViewStateB()
    data class Error(val mensaje: String) : ModuloViewStateB()
}

class ModuloViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<ModuloViewStateB>(ModuloViewStateB.Loading)
    val uiState: StateFlow<ModuloViewStateB> = _uiState

    init {
        cargarModulos()
    }

    private fun cargarModulos() {
        viewModelScope.launch {
            _uiState.value = ModuloViewStateB.Loading
            Log.d(TAG, "📡 Iniciando petición a /Modulo/GET…")
            try {
                val response = RetrofitClient.moduloApiService.obtenerTodosLosModulos()

                Log.d(TAG, "🏷 Código HTTP recibido: ${response.code()}")
                if (response.isSuccessful) {
                    val modulos: List<ModuloDto> = response.body() ?: emptyList()
                    Log.d(TAG, " Body de la respuesta: $modulos")
                    _uiState.value = ModuloViewStateB.Success(modulos)
                } else {
                    Log.e(TAG, "️ Error HTTP: ${response.code()} - ${response.message()}")
                    _uiState.value = ModuloViewStateB.Error("Error ${response.code()}: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e(TAG, " Excepción en Retrofit: ${e.localizedMessage}", e)
                _uiState.value = ModuloViewStateB.Error("Excepción: ${e.localizedMessage}")
            }
        }
    }
}
