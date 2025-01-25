package com.example.projectakhir.service


import com.example.projectakhir.model.AllVendorResponse
import com.example.projectakhir.model.Vendor
import com.example.projectakhir.model.VendorDetailResponse
import retrofit2.Response
import retrofit2.http.*

interface VendorService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    // Mendapatkan semua vendor
    @GET("vendor")
    suspend fun getAllVendor(): AllVendorResponse

    // Mendapatkan detail vendor berdasarkan ID
    @GET("vendor/{id_vendor}")
    suspend fun getVendorById(@Path("id_vendor") id_vendor: Int): VendorDetailResponse

    // Menambahkan vendor baru
    @POST("vendor/store")
    suspend fun insertVendor(@Body vendor: Vendor)

    // Memperbarui data vendor berdasarkan ID
    @PUT("vendor/{id_vendor}")
    suspend fun updateVendor(@Path("id_vendor") id_vendor: Int, @Body vendor: Vendor)

    // Menghapus vendor berdasarkan ID
    @DELETE("vendor/{id_vendor}")
    suspend fun deleteVendor(@Path("id_vendor") id_vendor: Int): Response<Void>
}
