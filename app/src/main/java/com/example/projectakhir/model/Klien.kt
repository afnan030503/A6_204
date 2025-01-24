package com.example.projectakhir.model

import kotlinx.serialization.Serializable

@Serializable
data class Klien(
    val id_klien: Int,
    val nama_klien: String,
    val kontak_klien: String
)
@Serializable
data class AllKlienResponse(
    val status: Boolean,
    val message: String,
    val data: List<Klien>
)

@Serializable
data class KlienDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Klien
)