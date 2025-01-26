package com.example.event.service



import com.example.projectakhir.model.Acara
import com.example.projectakhir.model.AcaraDetailResponse
import com.example.projectakhir.model.AllAcaraResponse
import retrofit2.Response
import retrofit2.http.*

interface AcaraService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    // Mendapatkan semua acara
    @GET("acara")
    suspend fun getAllAcara(): AllAcaraResponse

    // Mendapatkan detail acara berdasarkan ID
    @GET("acara/{id_acara}")
    suspend fun getAcaraById(@Path("id_acara") id_acara: Int): AcaraDetailResponse

    // Menambahkan acara baru
    @POST("acara/Store")
    suspend fun insertAcara(@Body acara: Acara)

    // Memperbarui data acara berdasarkan ID
    @PUT("acara/{id_acara}")
    suspend fun updateAcara(@Path("id_acara") id_acara: Int, @Body acara: Acara)

    // Menghapus acara berdasarkan ID
    @DELETE("acara/{id_acara}")
    suspend fun deleteAcara(@Path("id_acara") id_acara: Int): Response<Void>
}
