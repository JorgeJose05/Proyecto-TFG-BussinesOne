package com.example.proyectobussinesone.navigation

import com.example.proyectobussinesone.ApiService
import com.example.proyectobussinesone.navigation.models.PerfilDto
import com.example.proyectobussinesone.ui.viewmodel.Perfil
import javax.inject.Inject


class PerfilRepository @Inject constructor(
    private val api: ApiService
) {
    suspend fun getPerfil(id: Long): PerfilDto = api.getPerfil(id)
    suspend fun getAllPerfiles(): List<Perfil> {
        // Llamada Retrofit: GET /Perfil/GET
        val response = api.obtenerTodosLosPerfiles()
        if (response.isSuccessful) {
            // Asumimos que body() es List<PerfilDto>; aquí mapeamos a nuestro data class Perfil
            return response.body()!!.map { dto ->
                Perfil(
                    id = dto.id,
                    nombre = dto.nombre,
                    email = dto.email,
                    contrasena = dto.contrasena,
                    telefono = dto.telefono,
                    direccion = dto.direccion,
                    formacionAcademica = dto.formacionAcademica,
                    datosPersonales = dto.datosPersonales,
                    dni = dto.dni,
                    fechaNacimiento = dto.fechaNacimiento?.toString(),
                    numeroSeguridadSocial = dto.numeroSeguridadSocial,
                    iban = dto.iban
                )
            }
        } else {
            throw Exception("Error ${response.code()}: ${response.message()}")
        }
    }

    suspend fun obtenerPerfilPorId(id: Long): Perfil {
        val dto = getPerfil(id)
        return Perfil(
            id = dto.id,
            nombre = dto.nombre,
            email = dto.email,
            contrasena = dto.contrasena,
            telefono = dto.telefono,
            direccion = dto.direccion,
            formacionAcademica = dto.formacionAcademica,
            datosPersonales = dto.datosPersonales,
            dni = dto.dni,
            fechaNacimiento = dto.fechaNacimiento?.toString(),
            numeroSeguridadSocial = dto.numeroSeguridadSocial,
            iban = dto.iban
        )
    }

}
