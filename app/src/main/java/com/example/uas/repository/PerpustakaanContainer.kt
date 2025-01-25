package com.example.uas.repository

import com.example.uas.service_api.AnggotaService
import com.example.uas.service_api.BukuService
import com.example.uas.service_api.PeminjamanService
import com.example.uas.service_api.PengembalianService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val bukuRepository: BukuRepository
    val anggotaRepository: AnggotaRepository
    val peminjamanRepository: PeminjamanRepository
    val pengembalianRepository: PengembalianRepository
}

class PerpustakaanContainer : AppContainer {
    private val baseUrl = "http://192.168.1.8/perpus/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val bukuService: BukuService by lazy { retrofit.create(BukuService::class.java) }
    override val bukuRepository: BukuRepository by lazy { NetworkBukuRepository(bukuService) }

    private val anggotaService: AnggotaService by lazy { retrofit.create(AnggotaService::class.java) }
    override val anggotaRepository: AnggotaRepository by lazy { NetworkAnggotaRepository(anggotaService) }

    private val peminjamanService: PeminjamanService by lazy { retrofit.create(PeminjamanService::class.java) }
    override val peminjamanRepository: PeminjamanRepository by lazy { NetworkPeminjamanRepository(peminjamanService) }

    private val pengembalianService: PengembalianService by lazy { retrofit.create(PengembalianService::class.java) }
    override val pengembalianRepository: PengembalianRepository by lazy { NetworkPengembalianRepository(pengembalianService) }
}