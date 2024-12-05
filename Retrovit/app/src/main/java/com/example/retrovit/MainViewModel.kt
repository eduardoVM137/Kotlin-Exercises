package com.example.retrovit

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainViewModel(context: Context) : ViewModel() {
    private val database = AppDatabase.getDatabase(context)

    val postList: Flow<List<Post>> = database.postDao().getAllPost()



    fun fetchPostFromApi() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getPostList()
                if (response.isSuccessful) {
                    val postList = response.body()
                    if (postList != null) {
                        // Toma los primeros 5 elementos
                        val limitedKardexList = postList.take(5)
                        database.postDao().insertAll(limitedKardexList)
                        println("Se insertaron los primeros 5 objetos: $limitedKardexList")
                    } else {
                        println("El cuerpo de la respuesta está vacío.")
                    }
                } else {
                    println("Error en la respuesta: ${response.code()} - ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun deleteAllPost() {
        viewModelScope.launch {
            try {
                database.postDao().deleteAll()
                println("Todos los elementos fueron eliminados de la base de datos.")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }



    fun insertPost(post: Post) {
        viewModelScope.launch {
            database.postDao().insert(post)
        }
    }

    fun updatePost(post: Post) {
        viewModelScope.launch {
            database.postDao().update(post)
        }
    }

    fun deletePost(post: Post) {
        viewModelScope.launch {
            database.postDao().delete(post)
        }
    }
}

