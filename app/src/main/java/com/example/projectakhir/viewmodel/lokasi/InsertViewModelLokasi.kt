package com.example.projectakhir.viewmodel.lokasi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhir.model.Lokasi
import com.example.projectakhir.repository.LokasiRepository
import kotlinx.coroutines.launch

class InsertViewModelLokasi(private val lokasiRepo: LokasiRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertLokasiUiState())
        private set

    fun updateInsertLokasiState(insertUiEvent: InsertLokasiUiEvent) {
        uiState = InsertLokasiUiState(insertUiEvent = insertUiEvent)
    }

    fun insertLokasi() {
        viewModelScope.launch {
            try {
                lokasiRepo.insertLokasi(uiState.insertUiEvent.toLokasi())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertLokasiUiState(
    val insertUiEvent: InsertLokasiUiEvent = InsertLokasiUiEvent()
)

data class InsertLokasiUiEvent(
    val id_lokasi: Int = 0,
    val nama_lokasi: String = "",
    val alamat_lokasi: String = "",
    val kapasitas: Int = 0
)

fun Lokasi.toUiStateLokasi(): InsertLokasiUiState = InsertLokasiUiState(
    insertUiEvent = toInsertUiEvent()
)

fun InsertLokasiUiEvent.toLokasi(): Lokasi = Lokasi(
    id_lokasi = id_lokasi,
    nama_lokasi = nama_lokasi,
    alamat_lokasi = alamat_lokasi,
    kapasitas = kapasitas
)

fun Lokasi.toInsertUiEvent(): InsertLokasiUiEvent = InsertLokasiUiEvent(
    id_lokasi = id_lokasi,
    nama_lokasi = nama_lokasi,
    alamat_lokasi = alamat_lokasi,
    kapasitas = kapasitas
)