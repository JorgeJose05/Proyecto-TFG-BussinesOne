package com.example.proyectobussinesone.ComponenteProductos.models

data class ProductoPostRequestDto(
    val nombre: String,
    val precio: Double,
    val foto: String,
    val codigo: Long,
    val usuarioCreadorId: Long
)
