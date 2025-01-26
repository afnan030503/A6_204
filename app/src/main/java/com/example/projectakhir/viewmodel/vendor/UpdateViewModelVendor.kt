package com.example.projectakhir.viewmodel.vendor

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhir.model.Vendor
import com.example.projectakhir.repository.VendorRepository
import com.example.projectakhir.view.vendor.DestinasiUpdateVendor
import kotlinx.coroutines.launch

class UpdateViewModelVendor(
    savedStateHandle: SavedStateHandle,
    private val vendorRepo: VendorRepository
) : ViewModel() {

    // Retrieve the ID Vendor from SavedStateHandle
    val id_vendor: Int = checkNotNull(savedStateHandle[DestinasiUpdateVendor.ID_VENDOR]) // Replace with Vendor ID

    var uiState = mutableStateOf(InsertVendorUiState())
        private set

    init {
        getUVendor()
    }

    // Fetch the Vendor data using ID
    private fun getUVendor() {
        viewModelScope.launch {
            try {
                val vendor = vendorRepo.getVendorById(id_vendor)
                vendor?.let {
                    uiState.value = it.toInsertVendorUIState() // Update state with the fetched data
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Update the Vendor information
    fun updateVendor(id_vendor: Int, vendor: Vendor) {
        viewModelScope.launch {
            try {
                vendorRepo.updateVendor(id_vendor, vendor)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Update the UI state with a new InsertVendorUiEvent
    fun updateVendorState(insertUiEvent: InsertVendorUiEvent) {
        uiState.value = uiState.value.copy(insertUiEvent = insertUiEvent)
    }
}

// Extension function to convert Vendor to InsertVendorUiState
fun Vendor.toInsertVendorUIState(): InsertVendorUiState = InsertVendorUiState(
    insertUiEvent = this.toInsertVendorUiEvent()
)