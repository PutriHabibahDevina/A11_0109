package com.example.uas.repository

import com.example.uas.model.Peminjaman
import com.example.uas.service_api.PeminjamanService
import java.io.IOException

interface PeminjamanRepository {
    suspend fun getPeminjaman(): List<Peminjaman>
    suspend fun insertPeminjaman(peminjaman: Peminjaman)
    suspend fun updatePeminjaman(id_peminjaman: String, peminjaman: Peminjaman)
    suspend fun deletePeminjaman(id_peminjaman: String)
    suspend fun getPeminjamanById(id_peminjaman: String): Peminjaman
}

class NetworkPeminjamanRepository(
    private val peminjamanApiService: PeminjamanService
) : PeminjamanRepository {
    override suspend fun insertPeminjaman(peminjaman: Peminjaman) {
        peminjamanApiService.insertPeminjaman(peminjaman)
    }

    override suspend fun updatePeminjaman(id_peminjaman: String, peminjaman: Peminjaman) {
        peminjamanApiService.updatePeminjaman(id_peminjaman, peminjaman)
    }

    override suspend fun deletePeminjaman(id_peminjaman: String) {
        try {
            val response = peminjamanApiService.deletePeminjaman(id_peminjaman)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete Peminjaman. HTTP Status code: ${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getPeminjaman(): List<Peminjaman> = peminjamanApiService.getPeminjaman()
    override suspend fun getPeminjamanById(id_peminjaman: String): Peminjaman {
        return peminjamanApiService.getPeminjamanById(id_peminjaman)
    }
}