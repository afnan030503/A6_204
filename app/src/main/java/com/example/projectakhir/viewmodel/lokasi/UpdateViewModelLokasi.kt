package com.example.projectakhir.viewmodel.lokasi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhir.model.Lokasi
import com.example.projectakhir.repository.LokasiRepository
import com.example.projectakhir.view.lokasi.DestinasiUpdateLokasi
import kotlinx.coroutines.launch

class UpdateViewModelLokasi(
    savedStateHandle: SavedStateHandle,
    private val lokasiRepo: LokasiRepository
) : ViewModel() {

    // Mendapatkan idLokasi dari SavedStateHandle
    val id_lokasi: Int = checkNotNull(savedStateHandle[DestinasiUpdateLokasi.ID_LOKASI])

    var uiState = mutableStateOf(InsertLokasiUiState())
        private set

    init {
        getULokasi()
    }

    // Mengambil data lokasi berdasarkan idLokasi
    private fun getULokasi() {
        viewModelScope.launch {
            try {
                val lokasi = lokasiRepo.getLokasiById(id_lokasi)
                lokasi?.let {
                    uiState.value = it.toInsertUIState() // Update state dengan data yang diambil
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Memperbarui informasi lokasi
    fun updateLokasi(id_lokasi: Int, lokasi: Lokasi) {
        viewModelScope.launch {
            try {
                lokasiRepo.updateLokasi(id_lokasi, lokasi)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Memperbarui state UI dengan InsertLokasiUiEvent baru
    fun updateLokasiState(insertUiEvent: InsertLokasiUiEvent) {
        uiState.value = uiState.value.copy(insertUiEvent = insertUiEvent)
    }
}

// Ekstensi untuk mengonversi Lokasi menjadi InsertLokasiUiState
fun Lokasi.toInsertUIState(): InsertLokasiUiState = InsertLokasiUiState(
    insertUiEvent = toInsertUiEvent()
)