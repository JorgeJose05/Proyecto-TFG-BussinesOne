package com.example.proyectobussinesone

import com.example.proyectobussinesone.ComponenteKotlinPrueba.models.Fichaje
import com.example.proyectobussinesone.ComponenteKotlinPrueba.models.FichajePostRequestDto
import com.example.proyectobussinesone.ComponenteProductos.models.FacturaPostRequestDto
import com.example.proyectobussinesone.ComponenteProductos.models.Product
import com.example.proyectobussinesone.ComponenteProductos.models.ProductoPostRequestDto
import com.example.proyectobussinesone.navigation.models.PerfilDto
import com.example.proyectobussinesone.ui.screens.PerfilRequest
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path


interface ApiService {
    @GET("Modulo/GET")
    suspend fun obtenerTodosLosModulos(): Response<List<ModuloDto>>

    @POST("Producto/POST")
    fun crearProducto(
        @Body producto: ProductoPostRequestDto
    ): Call<Void>


    @GET("Producto/GET")
    suspend fun obtenerTodosLosProductos(): Response<List<Product>>

    @POST("Factura/POST")
    fun crearFacturaSimple(@Body dto: FacturaPostRequestDto): Call<FacturaPostRequestDto>

    @GET("Producto/GET/{id}")
    fun getProductoPorId(@Path("id") id: String): Call<Product>

    @GET("Fichaje/GET")
    suspend fun obtenerTodosLosFichajes(): Response<List<Fichaje>>

    @POST("Fichaje/POST")
    suspend fun crearFichaje(@Body dto: FichajePostRequestDto): Fichaje

    @PATCH("Perfil/PATCH/{id}")
    fun patchPerfil(@Path("id") id: Long,@Body patchDto: PerfilDto): Response<PerfilDto>

    @GET("Perfil/GET/{id}")
    suspend fun getPerfil(@Path("id") id: Long): PerfilDto

    @GET("Perfil/GET")
    suspend fun obtenerTodosLosPerfiles(): Response<List<PerfilDto>>

    @POST("Perfil/POST")
    suspend fun crearPerfil(@Body dto: PerfilRequest): Response<PerfilDto>
}
