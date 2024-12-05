
package com.example.ejerciciolazycol

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.ejerciciolazycol.ui.theme.EjercicioLazyColTheme

import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EjercicioLazyColTheme {
                MainScreen()
            }
        }
    }


    @Composable
    fun MainScreen() {
        val context = LocalContext.current
        val room = Room.databaseBuilder(context, DBPruebas::class.java, "kardex").build()

        LaunchedEffect(Unit) {
            // Insertar un registro
            room.daoKardex().agregarKardex(DKardex(nombre = "Lalo", calificacion = 6.66, creditos = 5))

            // Obtener todos los registros
            val kardexes = room.daoKardex().obtenerKardex()
            kardexes.forEach {
                println("Nombre: ${it.nombre}, Calificación: ${it.calificacion}, Créditos: ${it.creditos}")
            }
        }
    }


//    @Composable
//    fun MainScreen() {
//      val room= Room.databaseBuilder(this,DBPruebas::class.java,"kardex").build()
//        lifecycle.launch{
//            room.daoKardex().agregarKardex(DKardex("Lalo",6.66,5))
//            room.daoKardex().agregarKardex(DKardex("Lalo",6.66,5))
//
//
//        }
//
//
//    }
}
