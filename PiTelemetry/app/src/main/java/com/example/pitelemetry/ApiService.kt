package com.example.pitelemetry

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {
    @GET("api/telemetry")
    suspend fun getTelemetry(): Telemetry
}

object Api {
    private const val BASE_URL = "http://192.168.2.69:5000/"

    val service: ApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)
}