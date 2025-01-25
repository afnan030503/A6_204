package com.example.projectakhir.model


import kotlinx.serialization.Serializable

@Serializable
data class Lokasi(
    val id_lokasi: Int,
    val nama_lokasi: String,
    val alamat_lokasi: String,
    val kapasitas: Int
)
@Serializable
data class AllLokasiResponse(
    val status: Boolean,
    val message: String,
    val data: List<Lokasi>
)

@Serializable
data class LokasiDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Lokasi
)
