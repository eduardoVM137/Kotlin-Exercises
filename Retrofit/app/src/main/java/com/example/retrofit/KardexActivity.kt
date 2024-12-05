package com.example.retrofit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
@Composable
fun ListaKardexEditable(
    flowKardex: Flow<List<Kardex>>, // Cambiar el nombre para que coincida con la semántica
    onFetchData: () -> Unit,
    onSave: (Kardex) -> Unit,
    onDelete: (Kardex) -> Unit
) {
    // Recoger datos del flujo
    val kardexList by flowKardex.collectAsState(initial = emptyList())

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(
            onClick = onFetchData,
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            Text("Cargar Datos desde la API")
        }

        LazyColumn {
            items(kardexList) { kardex ->
                KardexItem(
                    kardex,
                    onEditClick = { /* Implementación de editar */ },
                    onDeleteClick = { onDelete(kardex) }
                )
            }
        }
    }
}


@Composable
fun KardexItem(kardex: Kardex, onEditClick: () -> Unit, onDeleteClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Título: ${kardex.title}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Contenido: ${kardex.body}", style = MaterialTheme.typography.bodySmall)
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
    Divider(modifier = Modifier.padding(vertical = 8.dp))
}
