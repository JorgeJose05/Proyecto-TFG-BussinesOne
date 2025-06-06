package com.example.proyectobussinesone.navigation

import com.example.proyectobussinesone.ApiService
import com.example.proyectobussinesone.navigation.models.PerfilDto
import javax.inject.Inject


class PerfilRepository @Inject constructor(
    private val api: ApiService
) {
    suspend fun getPerfil(id: Long): PerfilDto = api.getPerfil(id)
}
