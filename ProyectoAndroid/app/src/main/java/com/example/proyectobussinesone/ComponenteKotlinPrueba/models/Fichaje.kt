package com.example.proyectobussinesone.ComponenteKotlinPrueba.models

import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.time.LocalTime




data class Fichaje(
    @SerializedName("fichajeID")
    val fichajeID: Long,

    val empleado: Perfil,

    // JSON: "fecha": "2025-06-01"
    // Lo dejamos como String para que GSON no intente mapear a un objeto LocalDate
    val fecha: String,

    // JSON: "horaEntrada": "08:30:00"
    val horaEntrada: String,

    // JSON: "horaSalida": "17:00:00"  (puede ser null si no hay fichaje de salida)
    val horaSalida: String?,

    val tiempoReal: Boolean,

    // JSON: "createdAt": "2025-05-29T19:58:52.315646"
    val createdAt: String?
)