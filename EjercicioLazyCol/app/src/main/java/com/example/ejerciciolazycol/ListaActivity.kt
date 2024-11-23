package com.example.ejerciciolazycol

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember

import com.example.ejerciciolazycol.ui.theme.EjercicioLazyColTheme

class ListaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EjercicioLazyColTheme {
                val materias = remember { obtenerKardexEditable() }
                ListaKardexEditable(materias)
            }
        }
    }
}
