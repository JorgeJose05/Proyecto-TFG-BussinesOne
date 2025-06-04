package com.example.proyectobussinesone

import com.example.proyectobussinesone.ComponenteProductos.models.FacturaPostRequestDto
import com.example.proyectobussinesone.ComponenteProductos.models.Product
import com.example.proyectobussinesone.ComponenteProductos.models.ProductoPostRequestDto
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
}
