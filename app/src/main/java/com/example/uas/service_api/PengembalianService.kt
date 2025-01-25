package com.example.uas.service_api

import com.example.uas.model.Pengembalian
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface PengembalianService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET("bacapengembalian.php")
    suspend fun getPengembalian(): List<Pengembalian>

    @GET("baca1pengembalian.php/{id_pengembalian}")
    suspend fun getPengembalianById(@Query("id_pengembalian")id_pengembalian:String): Pengembalian

    @POST("insertpengembalian.php")
    suspend fun insertPengembalian(@Body pengembalian: Pengembalian)
}