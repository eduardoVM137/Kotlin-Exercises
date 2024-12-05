package com.example.room

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.example.room.ui.theme.RoomTheme
import kotlinx.coroutines.launch

class ListaActivity : ComponentActivity() {
    private val database by lazy { AppDatabase.getDatabase(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RoomTheme {
                ListaKardexEditable(
                    flowMaterias = database.kardexDao().getAll(),
                    onSave = { kardex ->
                        lifecycleScope.launch {
                            database.kardexDao().update(kardex)
                        }
                    },
                    onDelete = { kardex ->
                        lifecycleScope.launch {
                            database.kardexDao().delete(kardex)
                        }
                    },
                    onAdd = { nuevaMateria ->
                        lifecycleScope.launch {
                            database.kardexDao().insert(nuevaMateria)
                        }
                    }
                )
            }
        }

    }
}
