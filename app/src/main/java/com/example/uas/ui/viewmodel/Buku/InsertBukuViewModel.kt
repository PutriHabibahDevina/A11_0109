package com.example.uas.ui.viewmodel.Buku

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas.model.Buku
import com.example.uas.repository.BukuRepository
import kotlinx.coroutines.launch

class InsertBukuViewModel (private val book: BukuRepository):ViewModel(){
    var uiState by mutableStateOf(InsertBukuUiState())
        private set

    fun updateInsertBukuState(insertUiBuku: InsertUiBuku){
        uiState = InsertBukuUiState(insertUiBuku = insertUiBuku)
    }

    suspend fun insertBuku(){
        viewModelScope.launch {
            try {
                book.insertBuku(uiState.insertUiBuku.toBuku())
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}


data class InsertBukuUiState(
    val insertUiBuku : InsertUiBuku = InsertUiBuku()
)

data class InsertUiBuku(
    val id_buku: String = "",
    val judul: String = "",
    val penulis: String = "",
    val kategori: String = "",
    val status: String = ""
)

fun InsertUiBuku.toBuku(): Buku = Buku(
    id_buku = id_buku,
    judul = judul,
    penulis = penulis,
    kategori = kategori,
    status = status
)

fun Buku.toUiStateBuku():InsertBukuUiState = InsertBukuUiState(
    insertUiBuku = toInsertUiBuku()
)

fun Buku.toInsertUiBuku():InsertUiBuku = InsertUiBuku(
    id_buku = id_buku,
    judul = judul,
    penulis = penulis,
    kategori = kategori,
    status = status
)