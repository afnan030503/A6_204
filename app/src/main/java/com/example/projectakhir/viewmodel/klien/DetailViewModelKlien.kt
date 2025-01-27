package com.example.projectakhir.viewmodel.klien


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.projectakhir.model.Klien
import com.example.projectakhir.repository.KlienRepository
import com.example.projectakhir.view.klien.DestinasiDetailKlien
import kotlinx.coroutines.launch
import java.io.IOException

// Status UI untuk detail klien
sealed class DetailKlienUiState {
    data class Success(val klien: Klien) : DetailKlienUiState()
    object Error : DetailKlienUiState()
    object Loading : DetailKlienUiState()
}

class DetailViewModelKlien(
    savedStateHandle: SavedStateHandle,
    private val klienRepository: KlienRepository
) : ViewModel() {

    var klienDetailKlienState: DetailKlienUiState by mutableStateOf(DetailKlienUiState.Loading)
    private set

    private val _idklien: Int = savedStateHandle[DestinasiDetailKlien.ID_KLIEN]
        ?: throw IllegalArgumentException("ID_KLIEN harus disediakan dan berupa angka yang valid")



    init {
        getDetailKlienByid()
    }

    fun getDetailKlienByid() {
        viewModelScope.launch {
            klienDetailKlienState = DetailKlienUiState.Loading
            try {
                val klien = klienRepository.getKlienById(_idklien)
                klienDetailKlienState = DetailKlienUiState.Success(klien)
            } catch (e: IOException) {
                klienDetailKlienState = DetailKlienUiState.Error
                println("IOException: ${e.message}")
            } catch (e: HttpException) {
                klienDetailKlienState = DetailKlienUiState.Error
                println("HttpException: ${e.message}")
            } catch (e: Exception) {
                klienDetailKlienState = DetailKlienUiState.Error
                println("Unexpected error: ${e.message}")
            }

        }
    }
}

