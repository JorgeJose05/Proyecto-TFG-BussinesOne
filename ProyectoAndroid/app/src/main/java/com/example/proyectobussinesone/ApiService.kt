package com.example.proyectobussinesone

import com.example.proyectobussinesone.ComponenteKotlinPrueba.models.Fichaje
import com.example.proyectobussinesone.ComponenteKotlinPrueba.models.FichajePostRequestDto
import com.example.proyectobussinesone.ComponenteProductos.models.FacturaPostRequestDto
import com.example.proyectobussinesone.ComponenteProductos.models.Product
import com.example.proyectobussinesone.ComponenteProductos.models.ProductoPostRequestDto
import com.example.proyectobussinesone.navigation.models.PerfilDto
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface ApiService {
    @GET("Modulo/GET")
    suspend fun obtenerTodosLosModulos(): Response<List<ModuloDto>>

    @POST("Producto/POST")
    fun crearProducto(
        @Body producto: ProductoPostRequestDto
    ): Call<Void>

    @POST("Factura/POST")
    fun crearFacturaSimple(@Body dto: FacturaPostRequestDto): Call<Void?>

    @GET("Producto/GET/{id}")
    fun getProductoPorId(@Path("id") id: String): Call<Product>

    @GET("Fichaje/GET")
    suspend fun obtenerTodosLosFichajes(): Response<List<Fichaje>>

    @POST("Fichaje/POST")
    suspend fun crearFichaje(@Body dto: FichajePostRequestDto): Fichaje


    @GET("Perfil/GET/{id}")
    suspend fun getPerfil(@Path("id") id: Long): PerfilDto

    @GET("Perfil/GET")
    suspend fun obtenerTodosLosPerfiles(): Response<List<PerfilDto>>
}
