package com.example.uas.repository

import com.example.uas.model.Pengembalian
import com.example.uas.service_api.PengembalianService
import java.io.IOException

interface PengembalianRepository {
    suspend fun getPengembalian(): List<Pengembalian>
    suspend fun insertPengembalian(pengembalian: Pengembalian)
    suspend fun updatePengembalian(id_pengembalian: String, pengembalian: Pengembalian)
    suspend fun deletePengembalian(id_pengembalian: String)
    suspend fun getPengembalianById(id_pengembalian: String): Pengembalian
}

class NetworkPengembalianRepository(
    private val pengembalianApiService: PengembalianService
) : PengembalianRepository {
    override suspend fun insertPengembalian(pengembalian: Pengembalian) {
        pengembalianApiService.insertPengembalian(pengembalian)
    }

    override suspend fun updatePengembalian(id_pengembalian: String, pengembalian: Pengembalian) {
        pengembalianApiService.updatePengembalian(id_pengembalian, pengembalian)
    }

    override suspend fun deletePengembalian(id_pengembalian: String) {
        try {
            val response = pengembalianApiService.deletePengembalian(id_pengembalian)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete Pengembalian. HTTP Status code: ${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getPengembalian(): List<Pengembalian> = pengembalianApiService.getPengembalian()
    override suspend fun getPengembalianById(id_pengembalian: String): Pengembalian {
        return pengembalianApiService.getPengembalianById(id_pengembalian)
    }
}