package com.example.projectakhir.repository

import com.example.event.service.AcaraService
import com.example.projectakhir.model.Acara
import com.example.projectakhir.model.AllAcaraResponse
import okio.IOException

interface AcaraRepository {

    suspend fun getAcara(): AllAcaraResponse

    suspend fun insertAcara(acara: Acara)

    suspend fun updateAcara(id_acara: Int, acara: Acara)

    suspend fun deleteAcara(id_acara: Int)

    suspend fun getAcaraById(id_acara: Int): Acara

}

class NetworkAcaraRepository(
    private val acaraApiService: AcaraService
) : AcaraRepository {

    override suspend fun getAcara(): AllAcaraResponse =
        acaraApiService.getAllAcara()

    override suspend fun insertAcara(acara: Acara) {
        acaraApiService.insertAcara(acara)
    }

    override suspend fun updateAcara(id_acara: Int, acara: Acara) {
        acaraApiService.updateAcara(id_acara, acara)
    }

    override suspend fun deleteAcara(id_acara: Int) {
        try {
            val response = acaraApiService.deleteAcara(id_acara)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete Acara. HTTP Status Code: ${response.code()}"
                )
            } else {
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getAcaraById(id_acara: Int): Acara {
        return acaraApiService.getAcaraById(id_acara).data
    }
}

