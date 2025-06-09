package com.example.proyectobussinesone

import com.google.gson.annotations.SerializedName

data class ModuloDto(
    @SerializedName("id") val id: Long,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("grupo") val grupo: String,
    @SerializedName("icono") val icono: String,
    @SerializedName("descripcion") val descripcion: String,
    @SerializedName("disponible") val disponible: Boolean
)
