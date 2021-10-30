package com.eduardocodigo0.db

import android.content.Context
import com.eduardocodigo0.api.JokeService

class JokeRepository(context: Context) {

    private val db = AppDatabase.getDBInstance(context)
    private val api = JokeService.getJokeService()

    suspend fun saveJoke(joke: Joke) = db.jokeDao().insert(joke)
    suspend fun getJokes() = db.jokeDao().getAll()
    suspend fun getJokeById(id: Int) = db.jokeDao().getById(id)
    suspend fun deleteJoke(joke: Joke) = db.jokeDao().delete(joke)

    suspend fun getRandomJoke() = api.getRandomJoke()
}