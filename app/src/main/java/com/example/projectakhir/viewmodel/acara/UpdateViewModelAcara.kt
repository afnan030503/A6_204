package com.example.projectakhir.viewmodel.acara


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhir.model.Acara
import com.example.projectakhir.model.Klien
import com.example.projectakhir.model.Lokasi
import com.example.projectakhir.repository.AcaraRepository
import com.example.projectakhir.repository.KlienRepository
import com.example.projectakhir.repository.LokasiRepository
import com.example.projectakhir.view.acara.DestinasiUpdateAcara
import kotlinx.coroutines.launch

class UpdateViewModelAcara(
    savedStateHandle: SavedStateHandle,
    private val acaraRepo: AcaraRepository,
    private val kln: KlienRepository,
    private val lks: LokasiRepository
) : ViewModel() {

    // ID Acara dari SavedStateHandle
    val idAcara: Int = checkNotNull(savedStateHandle[DestinasiUpdateAcara.route])

    // State untuk menyimpan data UI
    var uiState = mutableStateOf(InsertAcaraUiState())
        private set
    var klienList = mutableStateOf<List<Klien>>(emptyList())

    var lokasiList = mutableStateOf<List<Lokasi>>(emptyList())
        private set

    init {
        fetchAcaraData()
        fetchKlien()
        fetchLokasi()
    }

    // Memuat data Acara berdasarkan ID
    private fun fetchAcaraData() {
        viewModelScope.launch {
            try {
                val acara = acaraRepo.getAcaraById(idAcara)
                acara?.let {
                    uiState.value = it.toInsertUIEvent()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Memuat data klien dan lokasi untuk dropdown
    private fun fetchKlien() {
        viewModelScope.launch {
            try {
                val response = kln.getKlien()
                klienList.value = response.data

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    private fun fetchLokasi(){
        viewModelScope.launch {
            try {
                val response = lks.getLokasi()
                lokasiList.value = response.data
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    // Fungsi untuk mengupdate data Acara
    // Fungsi untuk mengupdate data Acara
    fun updateAcara(id_acara: Int, acara: Acara) {
        viewModelScope.launch {
            try {
                acaraRepo.updateAcara(id_acara, acara)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    // Fungsi untuk memperbarui state InsertAcaraUiEvent
    fun updateAcaraState(insertUiEvent: InsertAcaraUiEvent) {
        uiState.value = uiState.value.copy(insertUiEvent = insertUiEvent)
    }
}

// Extension function untuk mengonversi Acara ke InsertAcaraUiState
fun Acara.toInsertUIEvent(): InsertAcaraUiState = InsertAcaraUiState(
    insertUiEvent = InsertAcaraUiEvent()
)