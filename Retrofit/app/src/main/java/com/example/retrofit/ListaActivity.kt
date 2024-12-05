package com.example.retrofit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.example.retrofit.ui.theme.RetrofitTheme
import kotlinx.coroutines.launch

class ListaActivity : ComponentActivity() {
    private val database by lazy { AppDatabase.getDatabase(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RetrofitTheme {
                ListaKardexEditable(
                    flowKardex = database.kardexDao().getAllKardex(), // Cambiar a "flowKardex"
                    onFetchData = {
                        lifecycleScope.launch {
                            // Aquí puedes llamar a tu API para sincronizar datos con Room
                        }
                    },
                    onSave = { kardex ->
                        lifecycleScope.launch {
                           // database.kardexDao().update(kardex)
                        }
                    },
                    onDelete = { kardex ->
                        lifecycleScope.launch {
                           // database.kardexDao().delete(kardex)
                        }
                    }
                )
            }
        }
    }
}
