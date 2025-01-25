package com.example.uas.model

import kotlinx.serialization.Serializable

@Serializable
data class Buku(
    val id_buku:String,
    val judul: String,
    val penulis: String,
    val kategori: String,
    val status: String
)