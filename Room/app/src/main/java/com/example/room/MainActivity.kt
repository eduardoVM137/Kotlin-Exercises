package com.example.room

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.room.ui.theme.RoomTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Instanciar la base de datos y obtener el DAO
        val database = DatabaseBuilder.getInstance(applicationContext)
        val itemDao = database.itemDao()

        setContent {
            RoomTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ItemListScreen(
                        itemDao = itemDao,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ItemListScreen(itemDao: ItemDao, modifier: Modifier = Modifier) {
    var newItemName by remember { mutableStateOf("") }
    val itemList by itemDao.getAllItems().collectAsState(initial = emptyList()) // Recibir datos de Room

    Column(modifier = modifier.padding(16.dp)) {
        // Campo de texto para agregar nuevos elementos
        BasicTextField(
            value = newItemName,
            onValueChange = { newItemName = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            decorationBox = { innerTextField ->
                Box(Modifier.padding(8.dp)) {
                    if (newItemName.isEmpty()) Text("Ingrese un elemento")
                    innerTextField()
                }
            }
        )
        Button(
            onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    if (newItemName.isNotBlank()) {
                        itemDao.insertItem(Item(name = newItemName))
                        newItemName = ""
                    }
                }
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Agregar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar la lista de elementos
        LazyColumn {
            items(itemList) { item ->
                Text(
                    text = item.name,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RoomTheme {
        Text("Vista previa no soporta Room")
    }
}
