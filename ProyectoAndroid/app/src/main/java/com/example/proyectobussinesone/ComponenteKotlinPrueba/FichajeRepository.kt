package com.example.proyectobussinesone.ComponenteKotlinPrueba

import android.util.Log
import com.example.proyectobussinesone.ComponenteKotlinPrueba.models.Fichaje
import com.example.proyectobussinesone.ComponenteKotlinPrueba.models.FichajePostRequestDto
import com.example.proyectobussinesone.RetrofitClient
import retrofit2.Response


class FichajeRepository {
    suspend fun getAllFichajes(): List<Fichaje> {
        val response = RetrofitClient.moduloApiService.obtenerTodosLosFichajes()
        Log.d("TimeTrackerVM", "   HTTP code: ${response.code()}")
        return if (response.isSuccessful) {

            // Si body() es null, lanzamos excepción o devolvemos lista vacía (según tu caso)
            response.body() ?: emptyList()
        } else {
            // Podrías lanzar una excepción personalizada para manejar el error más arriba
            throw Exception("Error ${response.code()}: ${response.message()}")
        }
    }

    suspend fun createFichaje(request: FichajePostRequestDto): Fichaje {
        try {
            return RetrofitClient.moduloApiService.crearFichaje(request)
        } catch (e: retrofit2.HttpException) {
            Log.e("FichajeRepo", "Error HTTP: ${e.code()} - ${e.message()}")
            throw e
        } catch (e: Exception) {
            Log.e("FichajeRepo", "Error general: ${e.message}")
            throw e
        }
    }


}