package com.example.proyectobussinesone.ComponenteProductos.models

import java.time.LocalDate


data class FacturaPostRequestDto(
    val fecha: String,
    val precioTotal: Double,
    val usuarioId: Long,
    val items: List<FacturaItemDto>
)