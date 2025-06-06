package com.example.proyectobussinesone.ComponenteKotlinPrueba.models

data class Perfil(
    val id: Long,
    val nombre: String?,
    val rol: String?,
    val email: String?,
    val direccion: String?,
    val infoPersonal: String?,
    val formacionAcademica: String?,
    val datosPersonales: String?
)
