package com.example.convertidor

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    LazyColumn {
        items(materias) { materia ->
            MateriaItem(materia)
        }
    }
}