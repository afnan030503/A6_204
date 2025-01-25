package com.example.projectakhir.viewmodel.lokasi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.projectakhir.model.Lokasi
import com.example.projectakhir.repository.LokasiRepository
import kotlinx.coroutines.launch
import java.io.IOException

// Status UI untuk halaman Home Lokasi
sealed class HomeLokasiUiState {
    data class Success(val lokasi: List<Lokasi>) : HomeLokasiUiState()
    object Error : HomeLokasiUiState()
    object Loading : HomeLokasiUiState()
}

class HomeViewModelLokasi(private val lks: LokasiRepository) : ViewModel() {

    // State untuk menyimpan status UI
    var lokasiUiState: HomeLokasiUiState by mutableStateOf(HomeLokasiUiState.Loading)
        private set

    init {
        // Memuat data lokasi saat ViewModel dibuat
        getLokasi()
    }

    // Fungsi untuk mengambil semua data lokasi
    fun getLokasi() {
        viewModelScope.launch {
            lokasiUiState = HomeLokasiUiState.Loading // Set status ke Loading

            lokasiUiState = try {
                // Ambil data lokasi dari repository
                HomeLokasiUiState.Success(lks.getLokasi().data)
            } catch (e: IOException) {
                HomeLokasiUiState.Error // Set status ke Error jika ada masalah jaringan
            } catch (e: HttpException) {
                HomeLokasiUiState.Error // Set status ke Error jika ada masalah HTTP
            }
        }
    }

    // Fungsi untuk menghapus lokasi berdasarkan ID
    fun deleteLokasi(id: Int) {
        viewModelScope.launch {
            try {
                // Hapus lokasi dari repository
                lks.deleteLokasi(id)
                // Perbarui daftar lokasi setelah menghapus
                getLokasi()
            } catch (e: IOException) {
                lokasiUiState = HomeLokasiUiState.Error
            } catch (e: HttpException) {
                lokasiUiState = HomeLokasiUiState.Error
            }
        }
    }
}