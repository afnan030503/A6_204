package com.example.projectakhir.viewmodel.klien

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhir.model.Klien
import com.example.projectakhir.repository.KlienRepository
import kotlinx.coroutines.launch

class InsertViewModelKlien(private val klienRepo: KlienRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertKlienUiState())
        private set

    fun updateInsertKlienState(insertUiEvent: InsertKlienUiEvent) {
        uiState = InsertKlienUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun insertKlien() {
        viewModelScope.launch {
            try {
                klienRepo.insertKlien(uiState.insertUiEvent.toKlien())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertKlienUiState(
    val insertUiEvent: InsertKlienUiEvent = InsertKlienUiEvent()
)

data class InsertKlienUiEvent(
    val id_klien: Int = 0,
    val nama_klien: String = "",
    val kontak_klien: String =""
)

fun Klien.toUiStateKlien(): InsertKlienUiState = InsertKlienUiState(
    insertUiEvent = toInsertUiEvent()
)

fun InsertKlienUiEvent.toKlien(): Klien = Klien(
    id_klien = id_klien,
    nama_klien = nama_klien,
    kontak_klien = kontak_klien
)

fun Klien.toInsertUiEvent(): InsertKlienUiEvent = InsertKlienUiEvent(
    id_klien = id_klien,
    nama_klien = nama_klien,
    kontak_klien = kontak_klien
)
