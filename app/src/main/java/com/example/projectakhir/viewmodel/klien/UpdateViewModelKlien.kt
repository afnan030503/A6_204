package com.example.projectakhir.viewmodel.klien


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhir.model.Klien
import com.example.projectakhir.repository.KlienRepository
import com.example.projectakhir.view.klien.DestinasiUpdateKlien
import kotlinx.coroutines.launch

class UpdateViewModelKlien(
    savedStateHandle: SavedStateHandle,
    private val klienRepo: KlienRepository
) : ViewModel() {

    // Retrieve the idKlien from SavedStateHandle
    val id_klien: Int = checkNotNull(savedStateHandle[DestinasiUpdateKlien.ID_KLIEN])

    var uiState = mutableStateOf(InsertKlienUiState())
        private set

    init {
        getUKlien()
    }

    // Fetch the client data using idKlien
    private fun getUKlien() {
        viewModelScope.launch {
            try {
                val klien = klienRepo.getKlienById(id_klien)
                klien?.let {
                    uiState.value = it.toInsertUIEvent() // Update state with the fetched data
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Update the client information
    fun updateKlien(id_klien: Int, klien: Klien) {
        viewModelScope.launch {
            try {
                klienRepo.updateKlien(id_klien, klien)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Update the UI state with a new InsertUiEvent
    fun updateKlienState(insertUiEvent: InsertKlienUiEvent) {
        uiState.value = uiState.value.copy(insertUiEvent = insertUiEvent)
    }
}

fun Klien.toInsertUIEvent(): InsertKlienUiState = InsertKlienUiState(
    insertUiEvent = this.toInsertUiEvent()
)
