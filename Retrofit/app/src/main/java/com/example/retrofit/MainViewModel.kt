package com.example.retrofit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val kardexDao = AppDatabase.getDatabase(application).kardexDao()
    val kardexList: Flow<List<Kardex>> = kardexDao.getAllKardex()

    // Obtener datos de la API y guardarlos en Room
    fun fetchKardexFromApi() {
        viewModelScope.launch {
            try {
                val kardexFromApi = RetrofitInstance.api.getPosts()
                kardexDao.insertAllKardex(kardexFromApi)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Actualizar un registro en Room
    fun updateKardex(kardex: Kardex) {
        viewModelScope.launch {
            kardexDao.update(kardex)
        }
    }

    // Eliminar un registro en Room
    fun deleteKardex(kardex: Kardex) {
        viewModelScope.launch {
            kardexDao.delete(kardex)
        }
    }
}
