package com.example.projectakhir

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.projectakhir.viewmodel.acara.DetailViewModelAcara
import com.example.projectakhir.viewmodel.acara.HomeViewModelAcara
import com.example.projectakhir.viewmodel.acara.InsertViewModelAcara
import com.example.projectakhir.viewmodel.acara.UpdateViewModelAcara
/*import com.example.projectakhir.viewmodel.acara.UpdateViewModelAcara*/
import com.example.projectakhir.viewmodel.klien.DetailViewModelKlien
import com.example.projectakhir.viewmodel.klien.HomeViewModelKlien
import com.example.projectakhir.viewmodel.klien.InsertViewModelKlien
import com.example.projectakhir.viewmodel.klien.UpdateViewModelKlien
import com.example.projectakhir.viewmodel.lokasi.DetailViewModelLokasi
import com.example.projectakhir.viewmodel.lokasi.HomeViewModelLokasi
import com.example.projectakhir.viewmodel.lokasi.InsertViewModelLokasi
import com.example.projectakhir.viewmodel.lokasi.UpdateViewModelLokasi
import com.example.projectakhir.viewmodel.vendor.DetailViewModelVendor
import com.example.projectakhir.viewmodel.vendor.HomeViewModelVendor
import com.example.projectakhir.viewmodel.vendor.InsertViewModelVendor
import com.example.projectakhir.viewmodel.vendor.UpdateViewModelVendor


object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { HomeViewModelKlien(aplikasiEvent().container.klienRepository) }
        initializer { InsertViewModelKlien(aplikasiEvent().container.klienRepository) }
        initializer {
            DetailViewModelKlien(
                createSavedStateHandle(),
                aplikasiEvent().container.klienRepository
            )
        }
        initializer {
            UpdateViewModelKlien(
                createSavedStateHandle(),
                aplikasiEvent().container.klienRepository
            )
        }

        initializer { HomeViewModelLokasi(aplikasiEvent().container.lokasiRepository) }
        initializer { InsertViewModelLokasi(aplikasiEvent().container.lokasiRepository) }
        initializer {
            DetailViewModelLokasi(
                createSavedStateHandle(),
                aplikasiEvent().container.lokasiRepository
            )
        }
        initializer {
            UpdateViewModelLokasi(
                createSavedStateHandle(),
                aplikasiEvent().container.lokasiRepository
            )
        }


        initializer { HomeViewModelVendor(aplikasiEvent().container.vendorRepository) }
        initializer { InsertViewModelVendor(aplikasiEvent().container.vendorRepository) }
        initializer {
            DetailViewModelVendor(
                createSavedStateHandle(),
                aplikasiEvent().container.vendorRepository
            )
        }
        initializer {
            UpdateViewModelVendor(
                createSavedStateHandle(),
                aplikasiEvent().container.vendorRepository
            )
        }

        initializer {
            HomeViewModelAcara(
                aplikasiEvent().container.acaraRepository,
                aplikasiEvent().container.klienRepository,
                aplikasiEvent().container.lokasiRepository
            )
        }
        initializer {
            InsertViewModelAcara(
                aplikasiEvent().container.acaraRepository,
                aplikasiEvent().container.klienRepository,
                aplikasiEvent().container.lokasiRepository
            )
        }
        initializer {
            DetailViewModelAcara(
                createSavedStateHandle(),
                aplikasiEvent().container.acaraRepository
            )
        }
        initializer {
            UpdateViewModelAcara(
                createSavedStateHandle(),
                aplikasiEvent().container.acaraRepository,
                aplikasiEvent().container.klienRepository,
                aplikasiEvent().container.lokasiRepository
            )
        }

    }
}

// Fungsi untuk mendapatkan instance aplikasi
fun CreationExtras.aplikasiEvent(): EventApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as EventApplication)
