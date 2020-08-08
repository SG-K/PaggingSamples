package com.example.paggingsample.network

import com.example.paggingsample.token
import okhttp3.Interceptor
import okhttp3.Response

//https://developer.spotify.com/console/get-browse-categories/?country=IN&locale=&limit=-22&offset=
class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer $token")
            .build()

        var response = chain.proceed(request)

        if (response.code() == 401) {

        }

        return response

    }
}