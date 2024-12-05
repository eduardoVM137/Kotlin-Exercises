package com.example.retrovit

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Post::class], version = 2, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "post_database"
                )
                    .fallbackToDestructiveMigration() // Destruye y recrea la base de datos si hay cambios de versión
                    .build()
                INSTANCE = instance
                instance
            }
        }


        // Define la migración de la versión 1 a la versión 2
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Agrega la nueva columna con un valor predeterminado
                database.execSQL("ALTER TABLE post ADD COLUMN nueva_columna TEXT DEFAULT '' NOT NULL")
            }
        }
    }

    private class DatabaseCallback : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    prepopulateDatabase(database.postDao())
                }
            }
        }

        suspend fun prepopulateDatabase(postDao: PostDao) {
            // Inserta datos iniciales en la base de datos
   //         kardexDao.insert(Kardex(title = "titulo Post", body = "Post", id = 1))
        //    kardexDao.insert(Kardex(title = "titulo Post", body = "Post", id = 2))
         //   kardexDao.insert(Kardex(title = "titulo Post", body = "Post de Física", id = 3))
        }
    }
}
