package com.zenith.sentinel

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class SentinelRepository {

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.NONE })
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://ip-api.com/") // Base placeholder
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(SentinelApi::class.java)

    suspend fun getIpData() = api.getIpLocation("http://ip-api.com/json")

    suspend fun getTimeData(timezone: String) = 
        api.getTime("http://worldtimeapi.org/api/timezone/$timezone")

    suspend fun getDetailedArea(country: String, zip: String) = 
        api.getZipData("http://api.zippopotam.us/$country/$zip")

    suspend fun getKnowledge(query: String) = 
        api.searchBooks("https://openlibrary.org/search.json?q=$query&limit=2")

    suspend fun getJoke() = 
        api.getJoke("https://v2.jokeapi.dev/joke/Any?type=single")
}