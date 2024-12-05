package com.example.ejerciciolazycol

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DaoKardex {
    @Query("SELECT * FROM DKardex")
    suspend fun obtenerKardex(): List<DKardex>  // Retorna una lista de DKardex

    @Query("SELECT * FROM DKardex WHERE nombre = :nombre")
    suspend fun obtenerKardexPorNombre(nombre: String): DKardex  // Retorna un solo objeto DKardex

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun agregarKardex(kardex: DKardex)

    @Query("UPDATE DKardex SET nombre = :nombre, calificacion = :calificacion, creditos = :creditos WHERE nombre = :nombre")
    suspend fun actualizarKardex(nombre: String, calificacion: Double, creditos: Int)

    @Query("DELETE FROM DKardex WHERE nombre = :nombre")
    suspend fun borrarKardex(nombre: String)
}
