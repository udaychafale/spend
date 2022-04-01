package com.example.spends.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayAPI
{
    @GET("/api/")
    suspend fun searchForImage(
        @Query("q") searchQuery: String,
        @Query("key") apiKey: String = "7811657-a345a2aeaecaa83b5b52e040b"
    ): Response<ImageResponse>
}