package com.example.paggingsample.network

import com.example.paggingsample.categories.models.CategoriesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SpotifyAPI {

    @GET("browse/categories")
    suspend fun getCategories(@Query("country") country : String,
                              @Query("limit") limit : String,
                              @Query("offset") offset : String) : CategoriesResponse
}