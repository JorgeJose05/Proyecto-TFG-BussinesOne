package com.example.proyectobussinesone.navigation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.proyectobussinesone.navigation.models.PerfilDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class PerfilViewModel(
    private val repository: PerfilRepository,
    private val usuarioId: Long
) : ViewModel() {

    private val _perfilState = MutableStateFlow<PerfilDto?>(null)
    val perfilState: StateFlow<PerfilDto?> = _perfilState

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        cargarPerfil()
    }

    private fun cargarPerfil() {
        viewModelScope.launch {
            try {
                val perfil = repository.getPerfil(usuarioId)
                _perfilState.value = perfil
            } catch (e: Exception) {
                _error.value = e.localizedMessage ?: "Error desconocido"
            }
        }
    }

    // ------------------------ Factory ------------------------
    class Factory(
        private val repository: PerfilRepository,
        private val usuarioId: Long
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PerfilViewModel::class.java)) {
                return PerfilViewModel(repository, usuarioId) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}