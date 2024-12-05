package com.example.ejerciciolazycol

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "DKardex")
data class DKardex(
    @PrimaryKey val nombre: String,
    val calificacion: Double,
    val creditos: Int
)







