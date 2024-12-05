package com.example.retrofit

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Kardex::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun kardexDao(): KardexDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "kardex_database"
                ).addCallback(DatabaseCallback()).build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class DatabaseCallback : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    prepopulateDatabase(database.kardexDao())
                }
            }
        }

        suspend fun prepopulateDatabase(kardexDao: KardexDao) {
            // Insertar datos iniciales
           // kardexDao.insert(Kardex(nombre = "Matemáticas", calificacion = 9.5, creditos = 5))
          //  kardexDao.insert(Kardex(nombre = "Historia", calificacion = 8.7, creditos = 3))
          //  kardexDao.insert(Kardex(nombre = "Física", calificacion = 7.8, creditos = 4))
        }
    }
}
