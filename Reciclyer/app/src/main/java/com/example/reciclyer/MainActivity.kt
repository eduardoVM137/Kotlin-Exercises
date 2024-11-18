package com.example.reciclyer


import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


import androidx.appcompat.app.AlertDialog
import ItemAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val items = listOf(
            Item("Item 0", R.drawable.ic_launcher_foreground),
            Item("Item 1", R.drawable.ic_launcher_foreground),
            Item("Eduardo Villagomez 10-31-2024 - 12:00pm", R.drawable.ic_launcher_foreground)
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ItemAdapter(items) { position ->
            showAlert(position) // Llamada a showAlert con la posición del elemento seleccionado
        }
    }

    private fun showAlert(position: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Elemento Seleccionado")
        builder.setMessage("Seleccionaste el elemento en la posición: $position")

        // Boton "Aceptar"
        builder.setPositiveButton("Aceptar") { dialog, _ ->
            dialog.dismiss()
        }

        // Boton "Cancelar" (opcional)
        builder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.dismiss()
        }

        // Mostrar el cuadro de dialogo
        val dialog = builder.create()
        dialog.show()
    }
}