package com.example.projectakhir.viewmodel.acara

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhir.model.Acara
import com.example.projectakhir.model.Klien
import com.example.projectakhir.model.Lokasi
import com.example.projectakhir.repository.AcaraRepository
import com.example.projectakhir.repository.KlienRepository
import com.example.projectakhir.repository.LokasiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class InsertViewModelAcara(
    private val acaraRepo: AcaraRepository,
    private val klienRepo: KlienRepository,
    private val lokasiRepo: LokasiRepository
) : ViewModel() {

    var uiState by mutableStateOf(InsertAcaraUiState())
        private set

    private val _klienList = MutableStateFlow<List<Klien>>(emptyList())
    val klienList: StateFlow<List<Klien>> = _klienList
    private val _lokasiList = MutableStateFlow<List<Lokasi>>(emptyList())
    val lokasiList : StateFlow<List<Lokasi>> = _lokasiList


    init {
        loadKlienList()
        loadLokasiList()
    }

    fun updateInsertAcaraState(insertUiEvent: InsertAcaraUiEvent) {
        uiState = InsertAcaraUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun insertAcara() {
        viewModelScope.launch {
            try {
                acaraRepo.insertAcara(uiState.insertUiEvent.toAcara())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun loadKlienList() {
        viewModelScope.launch {
            try {
                val response = klienRepo.getKlien()
                _klienList.value = response.data ?: emptyList()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun loadLokasiList() {
        viewModelScope.launch {
            try {
                val response = lokasiRepo.getLokasi()
                _lokasiList.value = response.data ?: emptyList()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertAcaraUiState(
    val insertUiEvent: InsertAcaraUiEvent = InsertAcaraUiEvent()
)

data class InsertAcaraUiEvent(
    val id_acara: Int = 0,
    val nama_acara: String = "",
    val deskripsi_acara: String = "",
    val tanggal_mulai: String = "",
    val tanggal_berakhir: String = "",
    val id_lokasi: Int = 0,
    val id_klien: Int = 0,
    val status_acara: String = ""
)

fun Acara.toUiStateAcara(): InsertAcaraUiState = InsertAcaraUiState(
    insertUiEvent = toInsertAcaraUiEvent()
)

fun InsertAcaraUiEvent.toAcara(): Acara = Acara(
    id_acara = id_acara,
    nama_acara = nama_acara,
    deskripsi_acara = deskripsi_acara,
    tanggal_mulai = tanggal_mulai,
    tanggal_berakhir = tanggal_berakhir,
    id_lokasi = id_lokasi,
    id_klien = id_klien,
    status_acara = status_acara
)

fun Acara.toInsertAcaraUiEvent(): InsertAcaraUiEvent = InsertAcaraUiEvent(
    id_acara = id_acara,
    nama_acara = nama_acara,
    deskripsi_acara = deskripsi_acara,
    tanggal_mulai = tanggal_mulai,
    tanggal_berakhir = tanggal_berakhir,
    id_lokasi = id_lokasi,
    id_klien = id_klien,
    status_acara = status_acara
)

enum class JenisStatus(val displayStatus: String) {
    DIRENCANAKAN("Direncanakan"),
    BERLANGSUNG("Berlangsung"),
    SELESAI("Selesai")
}
