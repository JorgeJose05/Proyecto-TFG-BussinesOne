package com.example.proyectobussinesone

import com.example.proyectobussinesone.ComponenteProductos.models.Product
import com.example.proyectobussinesone.ComponenteProductos.models.ProductoPostRequestDto
import retrofit2.Call
import retrofit2.http.GET

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @GET("Modulo/GET")
    suspend fun obtenerTodosLosModulos(): Response<List<ModuloDto>>

    @POST("Producto/POST")
    fun crearProducto(
        @Body producto: ProductoPostRequestDto
    ): Call<Void>


}
