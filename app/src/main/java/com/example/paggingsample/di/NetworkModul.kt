package com.example.paggingsample.di

import com.example.paggingsample.network.GithubPagingSource
import com.example.paggingsample.network.HeaderInterceptor
import com.example.paggingsample.network.PaggingSampleAPi
import com.example.paggingsample.network.PaggingSampleRepo
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun createRetrofitClient() =
    retrofitClient(baseUrl, okHttpClient())

val networkModule = module {
    single { createRetrofitClient().create(PaggingSampleAPi::class.java) }
    single { GithubPagingSource(get()) }
    single { PaggingSampleRepo(get()) }
}

private const val baseUrl = "https://api.spotify.com/v1/"

private fun okHttpClient() =
    OkHttpClient.Builder().run {
        addInterceptor(HttpLoggingInterceptor().apply{
            HttpLoggingInterceptor.Level.BODY
            readTimeout(60L, TimeUnit.SECONDS)
            connectTimeout(60L, TimeUnit.SECONDS)
            writeTimeout(60L, TimeUnit.SECONDS)
        })

        addInterceptor(HeaderInterceptor())

        build()
    }

private fun retrofitClient(baseUrl: String, httpClient: OkHttpClient): Retrofit =
    Retrofit.Builder().run {
        baseUrl(baseUrl)
        client(httpClient)
        addConverterFactory(GsonConverterFactory.create())
        build()
    }