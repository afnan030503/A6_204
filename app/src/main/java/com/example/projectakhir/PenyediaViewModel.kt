package com.example.projectakhir

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.projectakhir.viewmodel.klien.DetailViewModelKlien
import com.example.projectakhir.viewmodel.klien.HomeViewModelKlien
import com.example.projectakhir.viewmodel.klien.InsertViewModelKlien
import com.example.projectakhir.viewmodel.klien.UpdateViewModelKlien


object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { HomeViewModelKlien(aplikasiEvent().container.klienRepository) }
        initializer { InsertViewModelKlien(aplikasiEvent().container.klienRepository) }
        initializer { DetailViewModelKlien(createSavedStateHandle(), aplikasiEvent().container.klienRepository) }
        initializer { UpdateViewModelKlien(createSavedStateHandle(), aplikasiEvent().container.klienRepository) }

    }
}

// Fungsi untuk mendapatkan instance aplikasi
fun CreationExtras.aplikasiEvent(): EventApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as EventApplication)
