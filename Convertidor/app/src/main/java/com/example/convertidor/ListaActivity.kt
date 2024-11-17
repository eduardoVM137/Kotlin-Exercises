import com.example.convertidor.Kardex

fun obtenerKardex(): List<Kardex> {
    return listOf(
        Kardex("Matemáticas", 9.5, 5),
        Kardex("Historia", 8.7, 3),
        Kardex("Física", 7.8, 4),
        Materia("Programación", 10.0, 6),
        Materia("Química", 8.0, 4)
    )
}