package com.example.proyectobussinesone.navigation.models

import com.example.proyectobussinesone.ui.viewmodel.Perfil
import com.google.gson.annotations.SerializedName

data class PerfilDto(
    @SerializedName("id") val id: Long,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("contrasena") val contraseña: String?,
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

fun PerfilDto.toPerfil(): Perfil {
    return Perfil(
        id = this.id,
        nombre = this.nombre,
        email = this.email,
        contraseña = this.contraseña,
        telefono = this.telefono,
        direccion = this.direccion,
        formacionAcademica = this.formacionAcademica,
        datosPersonales = this.datosPersonales,
        dni = this.dni,
        fechaNacimiento = this.fechaNacimiento,
        numeroSeguridadSocial = this.numeroSeguridadSocial,
        iban = this.iban
    )
}
