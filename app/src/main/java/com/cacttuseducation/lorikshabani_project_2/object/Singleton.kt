package com.cacttuseducation.lorikshabani_project_2.`object`

import com.cacttuseducation.lorikshabani_project_2.service.APIService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Singleton {
    private const val BASE_URL = "http://10.0.2.2:8080"

    fun provideGson() : Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

    fun provideRetrofitInstance() : APIService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(provideGson()))
            .baseUrl(BASE_URL)
            .build()
            .create(APIService::class.java)
    }
}