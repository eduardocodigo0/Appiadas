package com.eduardocodigo0.db

import androidx.room.*

@Dao
interface JokeDao {

    @Query("SELECT * FROM joke")
    suspend fun getAll(): List<Joke>

    @Query("SELECT * FROM joke WHERE uid = (:id)")
    suspend fun getById(id: Int): Joke

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(joke: Joke)

    @Delete
    suspend fun delete(joke: Joke)

}