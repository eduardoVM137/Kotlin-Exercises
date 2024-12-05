package com.example.retrofit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.retrofit.ui.theme.RetrofitTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RetrofitTheme {
                val viewModel: MainViewModel = viewModel()
                ListaKardexEditable(
                    flowKardex = viewModel.kardexList,
                    onFetchData = { viewModel.fetchKardexFromApi() },
                    onSave = { viewModel.updateKardex(it) },
                    onDelete = { viewModel.deleteKardex(it) }
                )
            }
        }

    }
}
