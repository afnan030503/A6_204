package com.example.projectakhir.viewmodel.vendor

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
                vendorRepo.insertVendor(uiState.insertUiEvent.toVendor())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

@Composable
fun JenisVendorRadioButton(
    selectedJenisVendor: String,
    onJenisVendorChange: (String) -> Unit
) {
    // Semua nilai enum JenisVendor
    val jenisVendorOptions = JenisVendor.values()

    Column {
        Text(
            text = "Jenis Layanan Vendor",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Menampilkan RadioButton untuk setiap pilihan jenis vendor
        jenisVendorOptions.forEach { jenisVendor ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable { onJenisVendorChange(jenisVendor.displayNameVendor) }
            ) {
                RadioButton(
                    selected = selectedJenisVendor == jenisVendor.displayNameVendor,
                    onClick = { onJenisVendorChange(jenisVendor.displayNameVendor) }
                )
                Text(
                    text = jenisVendor.displayNameVendor,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 8.dp)
                )
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

