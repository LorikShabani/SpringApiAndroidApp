package com.cacttuseducation.lorikshabani_project_2.service

import com.cacttuseducation.lorikshabani_project_2.models.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface APIService {
    @POST("register")
    fun register(@Body newUser: RegisterRequest): Call<RegisterRequest>

    @POST("loginAttempt")
    fun loginAttempt(@Body user: LoginAttemptRequest): Call<LoginCodeRequest>

    @POST("login")
    fun loginCode(@Body code: LoginCodeRequest): Call<LoginCodeResponse>

    @POST("logout")
    fun logout(@Header("Authorization") token: String): Call<Void>

}

