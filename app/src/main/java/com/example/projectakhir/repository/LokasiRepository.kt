package com.example.projectakhir.repository


import com.example.event.service.LokasiService
import com.example.projectakhir.model.AllLokasiResponse
import com.example.projectakhir.model.Lokasi
import okio.IOException

interface LokasiRepository {

    suspend fun getLokasi(): AllLokasiResponse

    suspend fun insertLokasi(lokasi: Lokasi)

    suspend fun updateLokasi(id_lokasi: Int, lokasi: Lokasi)

    suspend fun deleteLokasi(id_lokasi: Int)

    suspend fun getLokasiById(id_lokasi: Int): Lokasi
}

class NetworkLokasiRepository(
    private val lokasiApiService: LokasiService
) : LokasiRepository {

    override suspend fun getLokasi(): AllLokasiResponse =
        lokasiApiService.getAllLokasi()

    override suspend fun insertLokasi(lokasi: Lokasi) {
        lokasiApiService.insertLokasi(lokasi)
    }

    override suspend fun updateLokasi(id_lokasi: Int, lokasi: Lokasi) {
        lokasiApiService.updateLokasi(id_lokasi, lokasi)
    }

    override suspend fun deleteLokasi(id_lokasi: Int) {
        try {
            val response = lokasiApiService.deleteLokasi(id_lokasi)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete Lokasi. HTTP Status Code: ${response.code()}"
                )
            } else {
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getLokasiById(id_lokasi: Int): Lokasi {
        return lokasiApiService.getLokasiById(id_lokasi).data
    }
}
