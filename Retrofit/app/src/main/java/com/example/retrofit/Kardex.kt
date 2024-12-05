package com.example.retrofit

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kardex")
data class Kardex(
    @PrimaryKey val id: Int, // Asegúrate de que este campo exista y esté anotado correctamente
    val title: String,
    val body: String
)
