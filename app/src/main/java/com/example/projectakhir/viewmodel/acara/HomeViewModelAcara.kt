package com.example.projectakhir.viewmodel.acara

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.projectakhir.model.Acara
import com.example.projectakhir.model.Klien
import com.example.projectakhir.model.Lokasi
import com.example.projectakhir.repository.AcaraRepository
import com.example.projectakhir.repository.KlienRepository
import com.example.projectakhir.repository.LokasiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

// Status UI untuk halaman Home Acara
sealed class HomeAcaraUiState {
    data class Success(val acara: List<Acara>) : HomeAcaraUiState()
    object Error : HomeAcaraUiState()
    object Loading : HomeAcaraUiState()
}

class HomeViewModelAcara(
    private val acr: AcaraRepository,
    private val kln: KlienRepository,
    private val lks: LokasiRepository
) : ViewModel() {

    // State untuk menyimpan status UI
    var acaraUiState: HomeAcaraUiState by mutableStateOf(HomeAcaraUiState.Loading)
        private set
    var klienMap = mutableStateOf<List<Klien>>(emptyList())

    var lokasiMap = mutableStateOf<List<Lokasi>>(emptyList())
        private set
    init {
        // Memuat data awal saat ViewModel dibuat
        getAcara()
        getKlien()
        getLokasi()
    }

    // Fungsi untuk mengambil semua data acara
    fun getAcara() {
        viewModelScope.launch {
            acaraUiState = HomeAcaraUiState.Loading // Set status ke Loading
            acaraUiState = try {
                HomeAcaraUiState.Success(acr.getAcara().data)
            } catch (e: IOException) {
                HomeAcaraUiState.Error
            } catch (e: HttpException) {
                HomeAcaraUiState.Error
            }
        }
    }
    private fun getKlien() {
        viewModelScope.launch {
            try {
                val response = kln.getKlien()
                klienMap.value = response.data
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    private fun getLokasi() {
        viewModelScope.launch {
            try {
                val response = lks.getLokasi()
                lokasiMap.value = response.data
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    // Fungsi untuk menghapus acara berdasarkan ID
    fun deleteAcara(id: Int) {
        viewModelScope.launch {
            acaraUiState = HomeAcaraUiState.Loading // Set status ke Loading
            try {
                // Hapus acara dari repository
                acr.deleteAcara(id)
                // Perbarui daftar acara setelah menghapus
                getAcara()
            } catch (e: IOException) {
                acaraUiState = HomeAcaraUiState.Error
            } catch (e: HttpException) {
                acaraUiState = HomeAcaraUiState.Error
            }
        }
    }
}