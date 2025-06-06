package com.example.proyectobussinesone.navigation.models

import com.google.gson.annotations.SerializedName

data class PerfilDto(
    @SerializedName("id") val id: Long,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("contrasena") val contrasena: String?,
    @SerializedName("email") val email: String,
    @SerializedName("telefono") val telefono: String?,
    @SerializedName("direccion") val direccion: String?,
    @SerializedName("dni") val dni: String?,
    @SerializedName("fechaNacimiento") val fechaNacimiento: String?,
    @SerializedName("numeroSeguridadSocial") val numeroSeguridadSocial: String?,
    @SerializedName("iban") val iban: String?,
    @SerializedName("formacionAcademica") val formacionAcademica: String?, // si lo devuelves como lista
    @SerializedName("datosPersonales") val datosPersonales: String?
)