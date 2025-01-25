package com.example.uas.repository

import com.example.uas.model.Anggota
import com.example.uas.service_api.AnggotaService
import java.io.IOException

interface AnggotaRepository {
    suspend fun getAnggota(): List<Anggota>
    suspend fun insertAnggota(anggota: Anggota)
    suspend fun updateAnggota(id_anggota: String, anggota: Anggota)
    suspend fun deleteAnggota(id_anggota: String)
    suspend fun getAnggotaById(id_anggota: String): Anggota
}

class NetworkAnggotaRepository(
    private val anggotaApiService: AnggotaService
) : AnggotaRepository {
    override suspend fun insertAnggota(anggota: Anggota) {
        anggotaApiService.insertAnggota(anggota)
    }

    override suspend fun updateAnggota(id_anggota: String, anggota: Anggota) {
        anggotaApiService.updateAnggota(id_anggota, anggota)
    }

    override suspend fun deleteAnggota(id_anggota: String) {
        try {
            val response = anggotaApiService.deleteAnggota(id_anggota)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete Anggota. HTTP Status code: ${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getAnggota(): List<Anggota> = anggotaApiService.getAnggota()
    override suspend fun getAnggotaById(id_anggota: String): Anggota {
        return anggotaApiService.getAnggotaById(id_anggota)
    }
}