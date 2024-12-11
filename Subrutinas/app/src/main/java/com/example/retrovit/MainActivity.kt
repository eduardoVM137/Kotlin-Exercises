package com.example.retrovit

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.retrovit.ui.theme.RetrovitTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat



import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat




class MainActivity : ComponentActivity() {

    private val database: AppDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "post_database"
        ).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNotificationChannel() // Crear canal de notificación
        setContent {
            RetrovitTheme {
                val viewModel = MainViewModel(applicationContext)

                var newPosts by remember { mutableStateOf<List<Post>>(emptyList()) }

                // UI principal con LazyColumn
                MainScreen(viewModel = viewModel)

                // Iniciar tarea repetitiva en segundo plano
                LaunchedEffect(Unit) {
                    startRepeatingTask(
                        onFetchPosts = { fetchedPosts ->
                            newPosts = fetchedPosts
                            if (newPosts.isNotEmpty()) {
                                newPosts.forEach { post ->
                                    viewModel.insertPost(post)
                                }
                                mostrarNotificacion("Tarea ejecutada correctamente")
                            }
                        }
                    )
                }
            }
        }
    }

    private suspend fun fetchPosts(): List<Post> {
        return try {
            val response = RetrofitInstance.api.getPostList()
            if (response.isSuccessful) {
                response.body()?.take(5) ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    private fun startRepeatingTask(onFetchPosts: (List<Post>) -> Unit) {
        lifecycleScope.launch(Dispatchers.IO) {
            while (true) {
                delay(5000) // Esperar 5 segundos
                val posts = fetchPosts()
                if (posts.isNotEmpty()) {
                    launch(Dispatchers.Main) {
                        onFetchPosts(posts)
                    }
                }
            }
        }
    }

    /**
     * Método para mostrar la notificación
     */
    private fun mostrarNotificacion(mensaje: String) {
        // Verificar si el permiso está concedido
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            val notificationId = 1
            val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Retrofit App")
                .setContentText(mensaje)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)

            with(NotificationManagerCompat.from(this)) {
                notify(notificationId, builder.build())
            }
        } else {
            // Solicitar el permiso si no está concedido
            solicitarPermisoNotificaciones()
        }
    }
    private fun solicitarPermisoNotificaciones() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Usar el registro para solicitar el permiso
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    // Registro para manejar la solicitud de permisos
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                // Permiso concedido, puedes mostrar la notificación
                mostrarNotificacion("Tarea ejecutada correctamente")
            } else {
                // Permiso denegado, maneja este caso si es necesario
                println("Permiso denegado para notificaciones")
            }
        }
    /**
     * Crea un canal de notificación si la versión de Android es superior a Oreo (API 26)
     */
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Canal de Tareas"
            val descriptionText = "Canal para notificaciones de tareas completadas"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        const val CHANNEL_ID = "canal_tareas"
    }
}

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val postList by viewModel.postList.collectAsState(initial = emptyList())

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Lista de Posts",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(postList) { post ->
                PostItem(post = post, onDelete = { viewModel.deletePost(post) })
            }
        }
    }
}

@Composable
fun PostItem(post: Post, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = post.title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = post.body, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onDelete) {
                Text("Eliminar")
            }
        }
    }
}


