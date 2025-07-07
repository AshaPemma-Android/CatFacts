package com.example.catfacts.data.remote.api

import com.example.catfacts.data.remote.dto.CatFactDto
import retrofit2.http.GET

interface CatFactApi {
    @GET("fact")
    suspend fun getCatFact(): CatFactDto
}