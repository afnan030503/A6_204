package com.example.projectakhir.viewmodel.klien

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.projectakhir.model.Klien
import com.example.projectakhir.repository.KlienRepository
import kotlinx.coroutines.launch
import java.io.IOException


// Status UI untuk halaman Home Klien
sealed class HomeKlienUiState {
    data class Success(val klien: List<Klien>) : HomeKlienUiState()
    object Error : HomeKlienUiState()
    object Loading : HomeKlienUiState()
}

class HomeViewModelKlien(private val kln: KlienRepository) : ViewModel() {

    // State untuk menyimpan status UI
    var klienUiState: HomeKlienUiState by mutableStateOf(HomeKlienUiState.Loading)
        private set

    init {
        // Memuat data klien saat ViewModel dibuat
        getKlien()
    }

    // Fungsi untuk mengambil semua data klien
    fun getKlien() {
        viewModelScope.launch {
            klienUiState = HomeKlienUiState.Loading // Set status ke Loading

            klienUiState = try {
                HomeKlienUiState.Success(kln.getKlien().data)
            } catch (e: IOException) {
                HomeKlienUiState.Error // Set status ke Error jika ada masalah jaringan
            } catch (e: HttpException) {
                HomeKlienUiState.Error // Set status ke Error jika ada masalah HTTP
            }
        }
    }

    // Fungsi untuk menghapus klien berdasarkan ID
    fun deleteKlien(id: Int) {
        viewModelScope.launch {
            try {
                // Hapus klien dari repository
                kln.deleteKlien(id)
                // Perbarui daftar klien setelah menghapus
            } catch (e: IOException) {
                klienUiState = HomeKlienUiState.Error
            } catch (e: HttpException) {
                klienUiState = HomeKlienUiState.Error
            }
        }
    }
}
