package com.example.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kardex")
data class Kardex(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // ID autogenerado
    val nombre: String,
    val calificacion: Double,
    val creditos: Int
)