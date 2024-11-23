package com.example.ejerciciolazycol

data class Kardex(
    val nombre: String,
    val calificacion: Double,
    val creditos: Int
)

fun obtenerKardexEditable(): MutableList<Kardex> {
    return mutableListOf(
        Kardex("Matemáticas", 9.5, 5),
        Kardex("Historia", 8.7, 3),
        Kardex("Física", 7.8, 4),
        Kardex("Programación", 10.0, 6),
        Kardex("Química", 8.0, 4)
    )
}
