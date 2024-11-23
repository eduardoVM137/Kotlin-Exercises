
package com.example.ejerciciolazycol

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.ejerciciolazycol.ui.theme.EjercicioLazyColTheme


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
        Button(onClick = {
            startActivity(Intent(this@MainActivity, ListaActivity::class.java))
        }) {
            Text("Ir a Lista Kardex")
        }
    }
}
