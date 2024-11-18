package com.example.notificacionescustoms

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Configuracion para manejar la ventana
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configuracion del boton para mostrar el cuadro de dialogo
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            showCustomDialog()
        }


    // Metodo para mostrar un cuadro de dialogo personalizado
    private fun showCustomDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmación #19100266")
        builder.setMessage("¿Desea continuar?")

        // Boton "Aceptar"
        builder.setPositiveButton("Aceptar") { dialog, _ ->
            // Accion   "Aceptar"
            dialog.dismiss()
        }

        // Boton "Cancelar"
        builder.setNegativeButton("Cancelar") { dialog, _ ->
            // Accion   "Cancelar"
            dialog.dismiss()
        }

        // Boton "Mejor no, otro dia con mas calma"
        builder.setNeutralButton("Mejor no, otro dia con mas calma") { dialog, _ ->
            // Accion
            dialog.dismiss()
        }

        // Mostrar el cuadro de dialogo
        val dialog = builder.create()
        dialog.show()
    }
}
