package com.example.projectakhir.model



import kotlinx.serialization.Serializable

@Serializable
data class Acara(
    val id_acara: Int,
    val nama_acara: String,
    val deskripsi_acara: String,
    val tanggal_mulai: String,
    val tanggal_berakhir: String,
    val id_lokasi: Int,
    val id_klien: Int,
    val status_acara: String
)


@Serializable
data class AllAcaraResponse(
    val status: Boolean,
    val message: String,
    val data: List<Acara>
)

@Serializable
data class AcaraDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Acara
)

