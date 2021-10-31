package com.eduardocodigo0.api

import com.eduardocodigo0.BASE_API_PATH
import com.eduardocodigo0.RANDOM_PATH
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface JokeService {
    @GET(RANDOM_PATH)
    suspend fun getRandomJoke(): JokeResponse

    companion object{
        fun getJokeService(): JokeService{
           return Retrofit.Builder()
                .baseUrl(BASE_API_PATH)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(JokeService::class.java)
        }
    }
}

