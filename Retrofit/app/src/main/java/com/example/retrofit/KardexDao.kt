package com.example.retrofit

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import kotlinx.coroutines.flow.Flow

@Dao
interface KardexDao {

    // Obtener todos los registros desde la base de datos
    @Query("SELECT * FROM kardex")
    fun getAllKardex(): Flow<List<Kardex>>

    // Insertar múltiples registros
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllKardex(kardexList: List<Kardex>)

    // Actualizar un registro específico
    @Update
    suspend fun update(kardex: Kardex)

    // Eliminar un registro específico
    @Delete
    suspend fun delete(kardex: Kardex)
}
