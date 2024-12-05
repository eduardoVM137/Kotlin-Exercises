package com.example.ejerciciolazycol

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DKardex::class], version = 1, exportSchema = false)
abstract class DBPruebas : RoomDatabase() {
    abstract fun daoKardex(): DaoKardex
}
