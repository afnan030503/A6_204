package com.example.projectakhir.viewmodel.vendor


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.projectakhir.model.Vendor
import com.example.projectakhir.repository.VendorRepository
import kotlinx.coroutines.launch
import java.io.IOException

// Status UI untuk halaman Home Vendor
sealed class HomeVendorUiState {
    data class Success(val vendor: List<Vendor>) : HomeVendorUiState()
    object Error : HomeVendorUiState()
    object Loading : HomeVendorUiState()
}

class HomeViewModelVendor(private val vdr: VendorRepository) : ViewModel() {

    // State untuk menyimpan status UI
    var vendorUiState: HomeVendorUiState by mutableStateOf(HomeVendorUiState.Loading)
        private set

    init {
        // Memuat data vendor saat ViewModel dibuat
        getVendor()
    }

    // Fungsi untuk mengambil semua data vendor
    fun getVendor() {
        viewModelScope.launch {
            vendorUiState = HomeVendorUiState.Loading // Set status ke Loading

            vendorUiState = try {
                // Ambil data vendor dari repository
                HomeVendorUiState.Success(vdr.getVendor().data)
            } catch (e: IOException) {
                HomeVendorUiState.Error // Set status ke Error jika ada masalah jaringan
            } catch (e: HttpException) {
                HomeVendorUiState.Error // Set status ke Error jika ada masalah HTTP
            }
        }
    }

    // Fungsi untuk menghapus vendor berdasarkan ID
    fun deleteVendor(id: Int) {
        viewModelScope.launch {
            try {
                // Hapus vendor dari repository
                vdr.deleteVendor(id)
                // Perbarui daftar vendor setelah menghapus
                getVendor()
            } catch (e: IOException) {
                vendorUiState = HomeVendorUiState.Error
            } catch (e: HttpException) {
                vendorUiState = HomeVendorUiState.Error
            }
        }
    }
}