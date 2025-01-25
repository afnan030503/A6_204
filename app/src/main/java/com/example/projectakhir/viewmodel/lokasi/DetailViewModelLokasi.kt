package com.example.projectakhir.viewmodel.lokasi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.projectakhir.model.Lokasi
import com.example.projectakhir.repository.LokasiRepository
import com.example.projectakhir.view.lokasi.DestinasiDetailLokasi
import kotlinx.coroutines.launch
import java.io.IOException


// Status UI untuk detail lokasi
sealed class DetailLokasiUiState {
    data class Success(val lokasi: Lokasi) : DetailLokasiUiState()
    object Error : DetailLokasiUiState()
    object Loading : DetailLokasiUiState()
}

// ViewModel untuk detail lokasi
class DetailViewModelLokasi(
    savedStateHandle: SavedStateHandle,
    private val lokasiRepository: LokasiRepository
) : ViewModel() {

    var lokasiDetailState: DetailLokasiUiState by mutableStateOf(DetailLokasiUiState.Loading)
        private set
    private val _idlokasi: Int = savedStateHandle[DestinasiDetailLokasi.ID_LOKASI]
        ?: throw IllegalArgumentException("ID_LOKASI harus disedikan dan berupa angka yanag valid")


    init {
        getDetailLokasiById()
    }

    fun getDetailLokasiById() {
        viewModelScope.launch {
            lokasiDetailState = DetailLokasiUiState.Loading
            try {
                // Fetch lokasi data dari repository
                val lokasi = lokasiRepository.getLokasiById(_idlokasi)
                lokasiDetailState = DetailLokasiUiState.Success(lokasi)
            } catch (e: IOException) {
                lokasiDetailState = DetailLokasiUiState.Error
                println("IOException: ${e.message}")
            } catch (e: HttpException) {
                lokasiDetailState = DetailLokasiUiState.Error
                println("HttpException: ${e.message}")
            } catch (e: Exception) {
                lokasiDetailState = DetailLokasiUiState.Error
                println("Unexpected error: ${e.message}")
            }
        }
    }
}
