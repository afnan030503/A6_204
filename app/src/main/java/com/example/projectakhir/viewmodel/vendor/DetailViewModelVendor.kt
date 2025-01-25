package com.example.projectakhir.viewmodel.vendor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.projectakhir.model.Vendor
import com.example.projectakhir.repository.VendorRepository
import com.example.projectakhir.view.vendor.DestinasiDetailVendor
import kotlinx.coroutines.launch
import java.io.IOException

// Status UI untuk detail vendor
sealed class DetailVendorUiState {
    data class Success(val vendor: Vendor) : DetailVendorUiState()
    object Error : DetailVendorUiState()
    object Loading : DetailVendorUiState()
}

class DetailViewModelVendor(
    savedStateHandle: SavedStateHandle,
    private val vendorRepository: VendorRepository
) : ViewModel() {

    var vendorDetailState: DetailVendorUiState by mutableStateOf(DetailVendorUiState.Loading)
        private set
    private val _idvendor: Int = savedStateHandle[DestinasiDetailVendor.ID_VENDOR]
        ?: throw IllegalArgumentException("ID_VENDOR harus disedikan dan berupa angka yanag valid")
    init {
        getDetailVendorById()
    }

    fun getDetailVendorById() {
        viewModelScope.launch {
            vendorDetailState = DetailVendorUiState.Loading
            try {
                // Fetch vendor data dari repository
                val vendor = vendorRepository.getVendorById(_idvendor)
                vendorDetailState = DetailVendorUiState.Success(vendor)
            }catch (e: IOException) {
                vendorDetailState = DetailVendorUiState.Error
                println("IOException: ${e.message}")
            } catch (e: HttpException) {
                vendorDetailState = DetailVendorUiState.Error
                println("HttpException: ${e.message}")
            } catch (e: Exception) {
                vendorDetailState = DetailVendorUiState.Error
                println("Unexpected error: ${e.message}")
            }
        }
    }
}
