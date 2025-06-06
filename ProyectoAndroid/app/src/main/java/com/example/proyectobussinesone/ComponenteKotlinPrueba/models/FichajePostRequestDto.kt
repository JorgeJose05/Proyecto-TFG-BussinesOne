package com.example.proyectobussinesone.ComponenteKotlinPrueba.models


import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.time.LocalTime

data class FichajePostRequestDto(
    @SerializedName("empleadoId")
    val empleadoId: Long,

    // Debe coincidir con el formato ISO que acepta tu backend, p.ej. "2025-06-01"
    @SerializedName("fecha")
    val fecha: String,

    @SerializedName("horaEntrada")
    val horaEntrada: String,

    @SerializedName("horaSalida")
    val horaSalida: String?,

    @SerializedName("tiempoReal")
    val tiempoReal: Boolean = true
)