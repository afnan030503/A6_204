package com.example.event.service

import com.example.projectakhir.model.AllLokasiResponse
import com.example.projectakhir.model.Lokasi
import com.example.projectakhir.model.LokasiDetailResponse
import retrofit2.Response
import retrofit2.http.*

interface LokasiService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    // Mendapatkan semua lokasi
    @GET("lokasi")
    suspend fun getAllLokasi(): AllLokasiResponse

    // Mendapatkan detail lokasi berdasarkan ID
    @GET("lokasi/{id_lokasi}")
    suspend fun getLokasiById(@Path("id_lokasi") id_lokasi: Int): LokasiDetailResponse

    // Menambahkan lokasi baru
    @POST("lokasi/Store")
    suspend fun insertLokasi(@Body lokasi: Lokasi)

    // Memperbarui data lokasi berdasarkan ID
    @PUT("lokasi/{id_lokasi}")
    suspend fun updateLokasi(@Path("id_lokasi") id_lokasi: Int, @Body lokasi: Lokasi)

    // Menghapus lokasi berdasarkan ID
    @DELETE("lokasi/{id_lokasi}")
    suspend fun deleteLokasi(@Path("id_lokasi") id_lokasi: Int): Response<Void>
}
