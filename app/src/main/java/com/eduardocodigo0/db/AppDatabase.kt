package com.eduardocodigo0.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Joke::class), version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun jokeDao(): JokeDao

    companion object{

        var db: AppDatabase? = null

        fun getDBInstance(context: Context): AppDatabase{
            lateinit var instance: AppDatabase

            db?.also { instance = it } ?: run{
                instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "joke-db"
                ).build()
                db = instance
            }

            return  instance

        }
    }
}

