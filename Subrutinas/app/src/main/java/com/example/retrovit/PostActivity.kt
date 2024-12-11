package com.example.retrovit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow

@Composable
fun ListaPostEditable(
    flowPost: Flow<List<Post>>,
    onFetchData: () -> Unit,
    onSave: (Post) -> Unit,
    onDelete: (Post) -> Unit,
    onAdd: (Post) -> Unit,
    onDeleteAll: () -> Unit,
) {
    // Obteniendo la lista de Post desde el flujo
    val postListState = flowPost.collectAsState(initial = emptyList())
    val postList = postListState.value // Acceder al valor del estado

    // Estados para el formulario emergente
    var showDialog by remember { mutableStateOf(false) }
    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }
    var editingPost: Post? by remember { mutableStateOf(null) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Botón para cargar datos desde la API
        Button(
            onClick = { onFetchData() },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            Text(text = "Cargar Datos desde la API")
        }

        // Botón para mostrar el formulario de agregar nueva materia
        Button(
            onClick = {
                editingPost = null
                title = ""
                body = ""
                showDialog = true
            },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            Text(text = "Agregar Nuevo Post")
        }

        // Botón para eliminar todas las materias
        Button(
            onClick = { onDeleteAll() },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            Text(text = "Eliminar Todos los Posts")
        }

        // Formulario emergente para agregar o editar Post
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = {
                    Text(text = if (editingPost == null) "Agregar Post" else "Editar Post")
                },
                text = {
                    Column {
                        OutlinedTextField(
                            value = title,
                            onValueChange = { title = it },
                            label = { Text(text = "Título") },
                            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                        )
                        OutlinedTextField(
                            value = body,
                            onValueChange = { body = it },
                            label = { Text(text = "Descripción") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (editingPost == null) {
                                // Agregar nuevo Post
                                onAdd(Post(title = title, body = body))
                            } else {
                                // Guardar edición
                                onSave(editingPost!!.copy(title = title, body = body))
                            }
                            showDialog = false
                        }
                    ) {
                        Text(text = if (editingPost == null) "Agregar" else "Guardar")
                    }
                },
                dismissButton = {
                    Button(onClick = { showDialog = false }) {
                        Text(text = "Cancelar")
                    }
                }
            )
        }

        // Lista de materias
        LazyColumn(modifier = Modifier.fillMaxSize().padding(top = 16.dp)) {
            items(postList) { post ->
                PostItem(
                    post = post,
                    onEditClick = { updatedPost ->
                        // Preparar el formulario para editar
                        editingPost = updatedPost
                        title = updatedPost.title
                        body = updatedPost.body
                        showDialog = true
                    },
                    onDeleteClick = { onDelete(post) }
                )
            }
        }
    }
}

@Composable
fun PostItem(
    post: Post,
    onEditClick: (Post) -> Unit,
    onDeleteClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Título: ${post.title}",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = "Descripción: ${post.body}",
            style = MaterialTheme.typography.bodyMedium
        )
        Row(
            modifier = Modifier.padding(top = 8.dp)
        ) {
            // Botón de editar
            Button(
                onClick = { onEditClick(post) },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text("Editar")
            }

            // Botón de eliminar
            Button(onClick = { onDeleteClick() }) {
                Text("Eliminar")
            }
        }
    }
    Divider(modifier = Modifier.padding(vertical = 8.dp))
}
