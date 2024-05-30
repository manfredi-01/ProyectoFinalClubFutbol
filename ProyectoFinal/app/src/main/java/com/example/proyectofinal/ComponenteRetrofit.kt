package com.example.proyectofinal

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.DELETE
import retrofit2.Retrofit

interface ServicioRetrofit{

    @GET("evento")
    suspend fun actualiza():List<Evento>

    @DELETE("evento/{id}")
    suspend fun borraUno(@Path("id") id:String):Evento
}

object ClienteRetrofit{
    private val retrofit= Retrofit.Builder()
        .baseUrl("https://65e1db45a8583365b31778eb.mockapi.io")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val servicio=retrofit.create(ServicioRetrofit::class.java)
}