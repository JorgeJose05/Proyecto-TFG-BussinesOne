package com.example.proyectobussinesone.navigation

import android.content.ContentValues.TAG
import android.util.Log
import com.example.proyectobussinesone.ApiService
import com.example.proyectobussinesone.navigation.models.PerfilDto
import com.example.proyectobussinesone.ui.screens.PerfilRequest
import com.example.proyectobussinesone.ui.viewmodel.Perfil
import retrofit2.Response
import javax.inject.Inject


class PerfilRepository @Inject constructor(
    private val api: ApiService
) {
    suspend fun getPerfil(id: Long): PerfilDto = api.getPerfil(id)
    suspend fun getAllPerfiles(): List<Perfil> {
        // Llamada Retrofit: GET /Perfil/GET
        Log.d(TAG, "Llamando a obtenerTodosLosPerfiles()...")
        val response = api.obtenerTodosLosPerfiles()

        Log.d(TAG, "Respuesta HTTP: code=${response.code()} isSuccessful=${response.isSuccessful}")

        if (response.isSuccessful) {
            val perfiles = response.body()

            Log.d(TAG, "Body recibido: ${perfiles?.size ?: 0} perfiles")

            perfiles?.forEach { dto ->
                Log.d(TAG, "Perfil recibido: email=${dto.email}, contraseña=${dto.contraseña}")
            }

            return perfiles?.filter { it.email != null && it.contraseña != null }?.map { dto ->
                Perfil(
                    id = dto.id,
                    nombre = dto.nombre,
                    email = dto.email,
                    contraseña = dto.contraseña,
                    telefono = dto.telefono,
                    direccion = dto.direccion,
                    formacionAcademica = dto.formacionAcademica,
                    datosPersonales = dto.datosPersonales,
                    dni = dto.dni,
                    fechaNacimiento = dto.fechaNacimiento?.toString(),
                    numeroSeguridadSocial = dto.numeroSeguridadSocial,
                    iban = dto.iban
                )
            } ?: emptyList()
        }
        return emptyList()
    }
    suspend fun crearPerfil(request: PerfilRequest): Response<PerfilDto> {
        return api.crearPerfil(request)
    }
    suspend fun obtenerPerfilPorId(id: Long): Perfil {
        val dto = getPerfil(id)
        return Perfil(
            id = dto.id,
            nombre = dto.nombre,
            email = dto.email,
            contraseña = dto.contraseña,
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
