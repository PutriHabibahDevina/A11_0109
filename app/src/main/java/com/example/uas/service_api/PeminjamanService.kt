package com.example.uas.service_api

import com.example.uas.model.Peminjaman
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface PeminjamanService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET("bacapeminjaman.php")
    suspend fun getPeminjaman(): List<Peminjaman>

    @GET("baca1peminjaman.php/{id_peminjaman}")
    suspend fun getPeminjamanById(@Query("id_peminjaman")id_peminjaman:String):Peminjaman

    @POST("insertpeminjaman.php")
    suspend fun insertPeminjaman(@Body peminjaman: Peminjaman)

    @PUT("editpeminjaman/php/{id_peminjaman}")
    suspend fun updatePeminjaman(@Query("id_peminjaman")id_peminjaman: String, @Body peminjaman: Peminjaman)

    @DELETE("deletepeminjaman.php/{id_peminjaman}")
    suspend fun deletePeminjaman(@Query("id_peminjaman")id_peminjaman: String):Response<Void>
}