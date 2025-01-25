package com.example.uas.service_api

import com.example.uas.model.Buku
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface BukuService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET("bacabuku.php")
    suspend fun getBuku(): List<Buku>

    @GET("baca1buku.php/{id_buku}")
    suspend fun getBukuById(@Query("id_buku")id_buku: String):Buku

    @POST("insertbuku.php")
    suspend fun insertBuku(@Body buku: Buku)

    @PUT("editbuku/php/{id_buku}")
    suspend fun updateBuku(@Query("id_buku")id_buku: String, @Body buku: Buku)

    @DELETE("deletebuku.php/{id_buku}")
    suspend fun deleteBuku(@Query("id_buku")id_buku: String):Response<Void>
}