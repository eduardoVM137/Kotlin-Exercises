package com.example.ejerciciolazycolumns

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.unit.dp


@Composable
fun MateriaItem(materia: Kardex) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Materia: ${materia.nombre}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Calificación: ${materia.calificacion}", style = MaterialTheme.typography.bodySmall)
        Text(text = "Créditos: ${materia.creditos}", style = MaterialTheme.typography.bodySmall)
    }
    Divider()
}


@Composable
fun ListaKardex(materias: List<Kardex>) {
    // Estado para controlar si se muestra la alerta
    var mostrarAlerta by remember { mutableStateOf(false) }
    var materiaSeleccionada by remember { mutableStateOf<Kardex?>(null) }

    LazyColumn {
        items(materias) { materia ->
            MateriaItem(materia) {
                materiaSeleccionada = materia
                mostrarAlerta = true
            }
        }
    }

    // Mostrar alerta cuando se selecciona un elemento
    if (mostrarAlerta && materiaSeleccionada != null) {
        AlertDialog(
            onDismissRequest = { mostrarAlerta = false },
            title = { Text("Información de Materia") },
            text = {
                Text("Materia: ${materiaSeleccionada?.nombre}\n" +
                        "Calificación: ${materiaSeleccionada?.calificacion}\n" +
                        "Créditos: ${materiaSeleccionada?.creditos}")
            },
            confirmButton = {
                Button(onClick = { mostrarAlerta = false }) {
                    Text("Aceptar")
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
            .clickable { onClick() } // Detecta el clic y llama a onClick
    ) {
        Text(text = "Materia: ${materia.nombre}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Calificación: ${materia.calificacion}", style = MaterialTheme.typography.bodySmall)
        Text(text = "Créditos: ${materia.creditos}", style = MaterialTheme.typography.bodySmall)
    }
    Divider()
}


