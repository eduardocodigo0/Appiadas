package com.eduardocodigo0.api

import com.eduardocodigo0.db.Joke
import com.google.gson.annotations.SerializedName

data class JokeResponse(

    @SerializedName("setup") val setup: String?,
    @SerializedName("delivery") val delivery: String?,
    @SerializedName("id") val id: Int?,
) {
    fun toJoke(): Joke{
        return Joke(uid = id ?: 0, setup = setup, delivery = delivery)
    }
}