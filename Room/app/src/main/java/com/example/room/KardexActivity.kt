package com.example.room

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow

@Composable
fun ListaKardexEditable(
    flowMaterias: Flow<List<Kardex>>,
    onSave: (Kardex) -> Unit,
    onDelete: (Kardex) -> Unit,
    onAdd: (Kardex) -> Unit // Nueva función para agregar materias
) {
    // Estados para controlar el formulario de edición y el formulario de agregar
    var mostrarAlerta by remember { mutableStateOf(false) }
    var mostrarFormularioAgregar by remember { mutableStateOf(false) }
    var materiaSeleccionada by remember { mutableStateOf<Kardex?>(null) }
    var nuevoNombre by remember { mutableStateOf("") }
    var nuevaCalificacion by remember { mutableStateOf("") }
    var nuevosCreditos by remember { mutableStateOf("") }

    // Recoger datos del flujo
    val materias by flowMaterias.collectAsState(initial = emptyList())

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Botón para agregar nueva materia
        Button(
            onClick = { mostrarFormularioAgregar = true },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            Text("Agregar Nueva Materia")
        }

        // Lista de materias
        LazyColumn {
            items(materias) { materia ->
                MateriaItem(
                    materia,
                    onEditClick = {
                        materiaSeleccionada = materia
                        nuevoNombre = materia.nombre
                        nuevaCalificacion = materia.calificacion.toString()
                        nuevosCreditos = materia.creditos.toString()
                        mostrarAlerta = true
                    },
                    onDeleteClick = { onDelete(materia) }
                )
            }
        }

        // Formulario para editar materia existente
        if (mostrarAlerta && materiaSeleccionada != null) {
            AlertDialog(
                onDismissRequest = { mostrarAlerta = false },
                title = { Text("Editar Materia") },
                text = {
                    Column {
                        TextField(
                            value = nuevoNombre,
                            onValueChange = { nuevoNombre = it },
                            label = { Text("Nombre de la materia") }
                        )
                        TextField(
                            value = nuevaCalificacion,
                            onValueChange = { nuevaCalificacion = it },
                            label = { Text("Calificación") }
                        )
                        TextField(
                            value = nuevosCreditos,
                            onValueChange = { nuevosCreditos = it },
                            label = { Text("Créditos") }
                        )
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        materiaSeleccionada?.let {
                            val materiaActualizada = it.copy(
                                nombre = nuevoNombre,
                                calificacion = nuevaCalificacion.toDoubleOrNull() ?: it.calificacion,
                                creditos = nuevosCreditos.toIntOrNull() ?: it.creditos
                            )
                            onSave(materiaActualizada)
                        }
                        mostrarAlerta = false
                    }) {
                        Text("Guardar")
                    }
                },
                dismissButton = {
                    Button(onClick = { mostrarAlerta = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }

        // Formulario para agregar nueva materia
        if (mostrarFormularioAgregar) {
            AlertDialog(
                onDismissRequest = { mostrarFormularioAgregar = false },
                title = { Text("Agregar Nueva Materia") },
                text = {
                    Column {
                        var nombre by remember { mutableStateOf("") }
                        var calificacion by remember { mutableStateOf("") }
                        var creditos by remember { mutableStateOf("") }

                        TextField(
                            value = nombre,
                            onValueChange = { nombre = it },
                            label = { Text("Nombre de la materia") },
                            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                        )
                        TextField(
                            value = calificacion,
                            onValueChange = { calificacion = it },
                            label = { Text("Calificación") },
                            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                        )
                        TextField(
                            value = creditos,
                            onValueChange = { creditos = it },
                            label = { Text("Créditos") },
                            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                        )
                        Button(
                            onClick = {
                                val nuevaMateria = Kardex(
                                    nombre = nombre,
                                    calificacion = calificacion.toDoubleOrNull() ?: 0.0,
                                    creditos = creditos.toIntOrNull() ?: 0
                                )
                                onAdd(nuevaMateria)
                                mostrarFormularioAgregar = false
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Guardar")
                        }
                    }
                },
                confirmButton = {},
                dismissButton = {}
            )
        }
    }
}

@Composable
fun MateriaItem(materia: Kardex, onEditClick: () -> Unit, onDeleteClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Materia: ${materia.nombre}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Calificación: ${materia.calificacion}", style = MaterialTheme.typography.bodySmall)
        Text(text = "Créditos: ${materia.creditos}", style = MaterialTheme.typography.bodySmall)
        Row {
            Button(onClick = onEditClick) {
                Text("Editar")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = onDeleteClick) {
                Text("Eliminar")
            }
        }
    }
    Divider()
}
