package com.example.ejerciciolazycol

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*

@Composable
fun ListaKardexEditable(materias: MutableList<Kardex>) {
    // Estado para controlar la alerta
    var mostrarAlerta by remember { mutableStateOf(false) }
    var materiaSeleccionada by remember { mutableStateOf<Kardex?>(null) }
    var nuevoNombre by remember { mutableStateOf("") }
    var nuevaCalificacion by remember { mutableStateOf("") }
    var nuevosCreditos by remember { mutableStateOf("") }

    LazyColumn {
        items(materias) { materia ->
            MateriaItem(materia) {
                materiaSeleccionada = materia
                nuevoNombre = materia.nombre
                nuevaCalificacion = materia.calificacion.toString()
                nuevosCreditos = materia.creditos.toString()
                mostrarAlerta = true
            }
        }
    }

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
                        val index = materias.indexOf(it)
                        if (index >= 0) {
                            materias[index] = it.copy(
                                nombre = nuevoNombre,
                                calificacion = nuevaCalificacion.toDoubleOrNull() ?: it.calificacion,
                                creditos = nuevosCreditos.toIntOrNull() ?: it.creditos
                            )
                        }
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
}

@Composable
fun MateriaItem(materia: Kardex, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onClick() }
    ) {
        Text(text = "Materia: ${materia.nombre}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Calificación: ${materia.calificacion}", style = MaterialTheme.typography.bodySmall)
        Text(text = "Créditos: ${materia.creditos}", style = MaterialTheme.typography.bodySmall)
    }
    Divider()
}
