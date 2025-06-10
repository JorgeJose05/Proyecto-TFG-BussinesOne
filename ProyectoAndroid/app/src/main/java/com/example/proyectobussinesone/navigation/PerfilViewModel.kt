// File: PerfilViewModel.kt
package com.example.proyectobussinesone.ui.viewmodel

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.proyectobussinesone.navigation.PerfilRepository
import com.example.proyectobussinesone.navigation.models.PerfilDto
import com.example.proyectobussinesone.navigation.models.toPerfil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class Perfil(
    val id: Long,
    val nombre: String,
    val email: String,
    val contraseña: String?,
    val telefono: String?,
    val direccion: String?,
    val formacionAcademica: String?,
    val datosPersonales: String?,
    val dni: String?,
    val fechaNacimiento: String?,
    val numeroSeguridadSocial: String?,
    val iban: String?
)

class PerfilViewModel(
    private val repository: PerfilRepository,
    private val usuarioId: Long? = null
) : ViewModel() {

    // — Estados para el flujo de login —
    sealed class LoginState {
        object Idle : LoginState()
        object Loading : LoginState()
        data class Success(val perfil: Perfil) : LoginState()
        data class Error(val message: String) : LoginState()
    }

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _perfilState = MutableStateFlow<Perfil?>(null)
    val perfilState: StateFlow<Perfil?> = _perfilState


    init {
        if (usuarioId != null) {
            viewModelScope.launch {
                try {
                    _perfilState.value = repository.obtenerPerfilPorId(usuarioId)
                } catch (e: Exception) {
                    _errorMessage.value = "Error cargando perfil: ${e.message}"
                }
            }
        }
    }

    /**
     * Trata de iniciar sesión comparando email y contraseña contra todos los perfiles de la API.
     */
    fun login(context: Context, email: String, contrasena: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            try {
                val listaPerfiles = repository.getAllPerfiles()
                Log.d(TAG, "LoginIntent: email='$email' | contrasena='$contrasena'")
                listaPerfiles.forEach { perfil ->
                    Log.d(TAG, "Perfil recibido → id=${perfil.id}, email='${perfil.email}', contrasena='${perfil.contraseña}'")
                }
                val match = listaPerfiles.firstOrNull {
                    it.email.equals(email, ignoreCase = true) &&
                            it.contraseña == contrasena
                }
                if (match != null) {
                    // GUARDAR ID EN SHARED PREFERENCES
                    val sharedPref = context.getSharedPreferences("UsuarioPrefs", Context.MODE_PRIVATE)
                    with(sharedPref.edit()) {
                        putLong("userId", match.id)
                        apply()
                    }

                    _loginState.value = LoginState.Success(match)
                } else {
                    _loginState.value = LoginState.Error("Credenciales incorrectas")
                    Log.d(TAG, "CONTRASEÑA: "+ contrasena+ " email: "+ email)


                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error("Error al conectar con el servidor")
                _errorMessage.value = e.message ?: e.toString()
                Log.d(TAG,e.message.toString())
            }
        }
    }

    fun patchPerfil(patchDto: PerfilDto) {
        val id = usuarioId ?: run {
            _errorMessage.value = "ID de usuario no definido"
            return
        }
        viewModelScope.launch {
            val result = repository.patchPerfil(usuarioId, patchDto)
            result.onSuccess { perfilActualizado ->
                _perfilState.value = perfilActualizado.toPerfil()
            }.onFailure {
                _errorMessage.value = it.message
            }
        }
    }


    /**
     * Factory para instanciar este ViewModel sin Hilt.
     * Simplemente recibe el repositorio.
     */
    class Factory(private val repo: PerfilRepository, private val usuarioId: Long?) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PerfilViewModel(repo, usuarioId) as T
        }
    }
}
