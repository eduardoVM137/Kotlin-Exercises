package com.example.retrovit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.example.retrovit.ui.theme.RetrovitTheme
import kotlinx.coroutines.launch

class ListaActivity : ComponentActivity() {
    private val database by lazy { AppDatabase.getDatabase(context = this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RetrovitTheme {
                ListaPostEditable(
                    flowPost = database.postDao().getAllPost(),
                    onFetchData = {
                        lifecycleScope.launch {
                            val postItems = fetchPostFromApi() // Asumiendo que `fetchKardexFromApi` está definido en ApiService.
                            database.postDao().insertAll(postItems)
                        }
                    },
                    onSave = { post ->
                        lifecycleScope.launch {
                            database.postDao().update(post)
                        }
                    },
                    onDelete = { post ->
                        lifecycleScope.launch {
                            database.postDao().delete(post)
                        }
                    },
                    onAdd = { post ->
                        lifecycleScope.launch {
                            database.postDao().insert(post)
                        }
                    },
                    onDeleteAll = { // Implementación del botón para eliminar todos los registros
                        lifecycleScope.launch {
                            database.postDao().deleteAll()
                        }
                    }

                )
            }
        }
    }

    private suspend fun fetchPostFromApi(): List<Post> {
        val apiService = RetrofitInstance.api
        return try {
            val response = apiService.getPostList() // Asegúrate de que este método devuelva Response<List<Kardex>>
            if (response.isSuccessful) {
                response.body() ?: emptyList() // Devuelve el cuerpo de la respuesta si es exitoso
            } else {
                emptyList() // Si la respuesta no es exitosa, devuelve una lista vacía
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList() // Manejo de errores
        }
    }



}
