package com.example.projectakhir.viewmodel.acara

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhir.model.Acara
import com.example.projectakhir.model.Klien
import com.example.projectakhir.model.Lokasi
import com.example.projectakhir.repository.AcaraRepository
import com.example.projectakhir.view.acara.DestinasiDetailAcara
import kotlinx.coroutines.launch

// Status UI untuk detail acara
sealed class DetailAcaraUiState {
    data class Success(val acara: Acara, val klien: Klien?, val lokasi: Lokasi?) : DetailAcaraUiState()
    object Error : DetailAcaraUiState()
    object Loading : DetailAcaraUiState()
}

class DetailViewModelAcara(
    savedStateHandle: SavedStateHandle,
    private val acaraRepository: AcaraRepository
) : ViewModel() {

    var acaraDetailState: DetailAcaraUiState by mutableStateOf(DetailAcaraUiState.Loading)
        private set

    val _idAcara: Int = savedStateHandle[DestinasiDetailAcara.ID_ACARA]
        ?: throw IllegalArgumentException("ID_ACARA harus disediakan dan berupa angka yang valid")

    // Menyimpan daftar klien dan lokasi untuk referensi
    private var klienList: List<Klien> = emptyList()
    private var lokasiList: List<Lokasi> = emptyList()

    init {
        getDetailAcara()
    }


    // Fungsi untuk mengambil detail acara
    fun getDetailAcara() {
        viewModelScope.launch {
            acaraDetailState = DetailAcaraUiState.Loading
            try {
                // Mengambil data acara berdasarkan ID
                val acara = acaraRepository.getAcaraById(_idAcara)
                if (acara != null) {
                    // Temukan klien dan lokasi terkait
                    val klien = klienList.find { it.id_klien == acara.id_klien }
                    val lokasi = lokasiList.find { it.id_lokasi == acara.id_lokasi }

                    // Emit state sukses
                    acaraDetailState = DetailAcaraUiState.Success(acara, klien, lokasi)
                } else {
                    // Emit state error jika acara tidak ditemukan
                    acaraDetailState = DetailAcaraUiState.Error
                }
            } catch (e: Exception) {
                acaraDetailState = DetailAcaraUiState.Error
                e.printStackTrace()
            }
        }
    }
}

// Fungsi untuk mengonversi data Acara ke UI Event
fun Acara.toDetailUiEvent(): InsertAcaraUiEvent {
    return InsertAcaraUiEvent(
        id_acara = id_acara,
        nama_acara = nama_acara,
        deskripsi_acara = deskripsi_acara,
        tanggal_mulai = tanggal_mulai,
        tanggal_berakhir = tanggal_berakhir,
        id_lokasi = id_lokasi,
        id_klien = id_klien,
        status_acara = status_acara
    )
}
