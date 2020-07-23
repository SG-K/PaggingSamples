package com.example.paggingsample.network

import com.example.paggingsample.categories.models.CategoriesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PaggingSampleAPi {

    @GET("browse/categories?country=IN&limit=3")
    fun getCategories() : Call<CategoriesResponse>
}