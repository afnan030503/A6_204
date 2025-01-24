package com.example.projectakhir.repository
import com.example.event.service.KlienService
import com.example.projectakhir.model.AllKlienResponse
import com.example.projectakhir.model.Klien
import okio.IOException

interface KlienRepository {

    suspend fun getKlien(): AllKlienResponse

    suspend fun insertKlien(klien: Klien)

    suspend fun updateKlien(id_klien: Int, klien: Klien)

    suspend fun deleteKlien(id_klien: Int)

    suspend fun getKlienById(id_klien: Int): Klien
}

class NetworkKlienRepository(
    private val klienApiService: KlienService
) : KlienRepository {

    override suspend fun getKlien(): AllKlienResponse =
        klienApiService.getAllKlien()

    override suspend fun insertKlien(klien: Klien) {
        klienApiService.insertKlien(klien)
    }

    override suspend fun updateKlien(id_klien: Int, klien: Klien) {
        klienApiService.updateKlien(id_klien, klien)
    }

    override suspend fun deleteKlien(id_klien: Int) {
        try {
            val response = klienApiService.deleteKlien(id_klien)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete Klien. HTTP Status Code: ${response.code()}"
                )
            } else {
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getKlienById(id_klien: Int): Klien {
        return klienApiService.getKlienById(id_klien).data
    }
}
