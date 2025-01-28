package com.example.projectakhir.viewmodel.vendor


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhir.model.Vendor
import com.example.projectakhir.repository.VendorRepository
import kotlinx.coroutines.launch

class InsertViewModelVendor(private val vendorRepo: VendorRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertVendorUiState())
        private set

    fun updateInsertVendorState(insertUiEvent: InsertVendorUiEvent) {
        uiState = InsertVendorUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun insertVendor() {
        viewModelScope.launch {
            try {
                val vendor = uiState.insertUiEvent.toVendor()
                println("Mengirim data vendor: $vendor")
                vendorRepo.insertVendor(vendor)
                println("Data vendor berhasil dikirim.")
            } catch (e: Exception) {
                println("Error saat mengirim data: ${e.localizedMessage}")
            }
        }
    }

}


data class InsertVendorUiState(
    val insertUiEvent: InsertVendorUiEvent = InsertVendorUiEvent()
)

data class InsertVendorUiEvent(
    val id_vendor: Int = 0,
    val nama_vendor: String = "",
    val jenis_vendor: String = "",
    val kontak_vendor: String = ""
)

fun Vendor.toUiStateVendor(): InsertVendorUiState = InsertVendorUiState(
    insertUiEvent = toInsertVendorUiEvent()
)

fun InsertVendorUiEvent.toVendor(): Vendor = Vendor(
    id_vendor = id_vendor,
    nama_vendor = nama_vendor,
    jenis_vendor = jenis_vendor,
    kontak_vendor = kontak_vendor
)

fun Vendor.toInsertVendorUiEvent(): InsertVendorUiEvent = InsertVendorUiEvent(
    id_vendor = id_vendor,
    nama_vendor = nama_vendor,
    jenis_vendor = jenis_vendor,
    kontak_vendor = kontak_vendor
)
enum class JenisVendor(val displayNameVendor: String) {
    CATERING("Katering"),
    DECORATION("Dekorasi"),
    AUDIO_VISUAL("Audio Visual"),
    TRANSPORTATION("Dll")
}

