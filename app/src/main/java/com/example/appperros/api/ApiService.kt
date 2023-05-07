package com.example.appperros.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
    @GET
    suspend fun getDogsByBreeds(@Url url:String): Response<DogsResponse>

    @GET
    suspend fun getBreedbyDog(@Url url:String): Response<DogsResponse>
}