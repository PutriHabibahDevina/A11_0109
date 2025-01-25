package com.example.uas.repository

import com.example.uas.model.Pengembalian
import com.example.uas.service_api.PengembalianService
import java.io.IOException

interface PengembalianRepository {
    suspend fun getPengembalian(): List<Pengembalian>
    suspend fun insertPengembalian(pengembalian: Pengembalian)
    suspend fun getPengembalianById(id_pengembalian: String): Pengembalian
}

class NetworkPengembalianRepository(
    private val pengembalianApiService: PengembalianService
) : PengembalianRepository {
    override suspend fun insertPengembalian(pengembalian: Pengembalian) {
        pengembalianApiService.insertPengembalian(pengembalian)
    }

    override suspend fun getPengembalian(): List<Pengembalian> = pengembalianApiService.getPengembalian()
    override suspend fun getPengembalianById(id_pengembalian: String): Pengembalian {
        return pengembalianApiService.getPengembalianById(id_pengembalian)
    }
}