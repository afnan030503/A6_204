package com.example.projectakhir.repository


import com.example.projectakhir.model.AllVendorResponse
import com.example.projectakhir.model.Vendor
import com.example.projectakhir.service.VendorService
import okio.IOException

interface VendorRepository {

    suspend fun getVendor(): AllVendorResponse

    suspend fun insertVendor(vendor: Vendor)

    suspend fun updateVendor(id_vendor: Int, vendor: Vendor)

    suspend fun deleteVendor(id_vendor: Int)

    suspend fun getVendorById(id_vendor: Int): Vendor
}

class NetworkVendorRepository(
    private val vendorApiService: VendorService
) : VendorRepository {

    override suspend fun getVendor(): AllVendorResponse =
        vendorApiService.getAllVendor()

    override suspend fun insertVendor(vendor: Vendor) {
        vendorApiService.insertVendor(vendor)
    }

    override suspend fun updateVendor(id_vendor: Int, vendor: Vendor) {
        vendorApiService.updateVendor(id_vendor, vendor)
    }

    override suspend fun deleteVendor(id_vendor: Int) {
        try {
            val response = vendorApiService.deleteVendor(id_vendor)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete Vendor. HTTP Status Code: ${response.code()}"
                )
            } else {
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getVendorById(id_vendor: Int): Vendor {
        return vendorApiService.getVendorById(id_vendor).data
    }
}
