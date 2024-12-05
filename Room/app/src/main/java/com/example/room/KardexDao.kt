package com.example.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface KardexDao {
    @Query("SELECT * FROM kardex")
    fun getAll(): Flow<List<Kardex>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(kardex: Kardex)

    @Update
    suspend fun update(kardex: Kardex)

    @Delete
    suspend fun delete(kardex: Kardex)
}
