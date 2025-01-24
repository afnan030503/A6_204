package com.example.event.service


import com.example.projectakhir.model.AllKlienResponse
import com.example.projectakhir.model.Klien
import com.example.projectakhir.model.KlienDetailResponse
import retrofit2.Response
import retrofit2.http.*

interface KlienService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    // Mendapatkan semua klien
    @GET("klien")
    suspend fun getAllKlien(): AllKlienResponse

    // Mendapatkan detail klien berdasarkan ID
    @GET("klien/{id_klien}")
    suspend fun getKlienById(@Path("id_klien") id_klien: Int): KlienDetailResponse

    // Menambahkan klien baru
    @POST("klien/store")
    suspend fun insertKlien(@Body klien: Klien)

    // Memperbarui data klien berdasarkan ID
    @PUT("klien/{id_klien}")
    suspend fun updateKlien(@Path("id_klien") id_klien: Int, @Body klien: Klien)

    // Menghapus klien berdasarkan ID
    @DELETE("klien/{id_klien}")
    suspend fun deleteKlien(@Path("id_klien") id_klien: Int): Response<Void>
}
