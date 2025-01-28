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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UpdateViewModelAcara(
    savedStateHandle: SavedStateHandle,
    private val acaraRepo: AcaraRepository,
    private val klnRepo: KlienRepository,
    private val lksRepo: LokasiRepository
) : ViewModel() {

    // ID Acara from SavedStateHandle
    val id_acara: Int = checkNotNull(savedStateHandle[DestinasiUpdateAcara.ID_ACARA])

    // State to store UI data
    var uiState = mutableStateOf(InsertAcaraUiState())
        private set
    val lokasiUiState: StateFlow<HomeLokasiUiState> = MutableStateFlow(
        HomeLokasiUiState(lokasiList = emptyList(), isLoading = true)
    )



    private val _klienList = MutableStateFlow<List<Klien>>(emptyList())
    val klienList: StateFlow<List<Klien>> = _klienList
    private val _lokasiList = MutableStateFlow<List<Lokasi>>(emptyList())
    val lokasiList: StateFlow<List<Lokasi>> = _lokasiList

    init {
        fetchAcaraData()
        loadKlienList()
        loadLokasiList()
    }

    // Load Acara data by ID
    private fun fetchAcaraData() {
        viewModelScope.launch {
            try {
                val acara = acaraRepo.getAcaraById(id_acara)
                acara?.let {
                    uiState.value = it.toInsertUIEvent()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Load Klien and Lokasi data for dropdown
    private fun loadKlienList() {
        viewModelScope.launch {
            try {
                val response = klnRepo.getKlien()
                _klienList.value = response.data ?: emptyList()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun loadLokasiList() {
        viewModelScope.launch {
            try {
                val response = lksRepo.getLokasi()
                _lokasiList.value = response.data ?: emptyList()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Update Acara data
    fun updateAcara(id_acara: Int, acara: Acara) {
        viewModelScope.launch {
            try {
                acaraRepo.updateAcara(id_acara, acara)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Update InsertAcaraUiEvent state
    fun updateAcaraState(insertUiEvent: InsertAcaraUiEvent) {
        uiState.value = uiState.value.copy(insertUiEvent = insertUiEvent)
    }
}

data class HomeLokasiUiState(
    val lokasiList: List<Lokasi> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

fun Acara.toInsertUIEvent(): InsertAcaraUiState = InsertAcaraUiState(
    insertUiEvent = this.toInsertAcaraUiEvent()
)