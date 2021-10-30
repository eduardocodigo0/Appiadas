package com.eduardocodigo0.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "joke", indices = [Index(value = ["setup"], unique = true)])
data class Joke(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "setup", ) val setup: String?,
    @ColumnInfo(name = "delivery") val delivery: String?,
)