package com.example.retrovit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import com.example.retrovit.ui.theme.RetrovitTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RetrovitTheme {
                val viewModel = MainViewModel(applicationContext)


                ListaPostEditable(
                    flowPost = viewModel.postList, // Cambia esto según tu lógica
                    onFetchData = { viewModel.fetchPostFromApi() }, // Lógica para cargar datos
                    onSave = { post -> viewModel.updatePost(post) }, // Actualizar un kardex
                    onDelete = { post -> viewModel.deletePost(post) }, // Eliminar un kardex
                    onAdd = { newPost -> viewModel.insertPost(newPost) },
                    onDeleteAll  = {   viewModel.deleteAllPost()}// Agregar un nuevo kardex
                )

            }
        }

    }
}
