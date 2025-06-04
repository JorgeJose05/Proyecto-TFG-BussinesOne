package com.example.proyectobussinesone.ComponenteProductos.models

data class FacturaItemDto(
    val productoId: Long,
    val cantidad: Int,
    val precioUnitario: Double
)