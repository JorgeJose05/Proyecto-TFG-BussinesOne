package com.example.proyectobussinesone.ComponenteKotlinPrueba

import com.example.proyectobussinesone.ComponenteKotlinPrueba.models.Fichaje


sealed class FichajeViewState {
    object Loading : FichajeViewState()
    data class Success(val listaFichajes: List<Fichaje>) : FichajeViewState()
    data class Error(val mensaje: String) : FichajeViewState()
}