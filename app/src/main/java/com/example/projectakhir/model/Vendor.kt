package com.example.projectakhir.model
import kotlinx.serialization.Serializable

@Serializable
data class Vendor(
    val id_vendor: Int,
    val nama_vendor: String,
    val jenis_vendor: String,
    val kontak_vendor: String
)
@Serializable
data class AllVendorResponse(
    val status: Boolean,
    val message: String,
    val data: List<Vendor>
)

@Serializable
data class VendorDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Vendor
)