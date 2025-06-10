package com.example.proyectobussinesone


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    //private const val BASE_URL = "http://bussinessone.us-east-1.elasticbeanstalk.com:8080/"
    //private const val BASE_URL = "http://192.168.56.1:8080/"
    private const val BASE_URL = "http://192.168.93.247:8080/"

        @JvmStatic
    val moduloApiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

}
