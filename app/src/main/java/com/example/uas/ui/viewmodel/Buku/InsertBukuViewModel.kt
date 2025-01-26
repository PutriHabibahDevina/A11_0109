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
    var uiState by mutableStateOf(InsertUiState())
        private set

    fun updateInsertBukuState(insertUiEvent: InsertUiBuku){
        uiState = InsertUiState(insertUiBuku = insertUiEvent)
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


data class InsertUiState(
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

fun Buku.toUiStateBuku():InsertUiState = InsertUiState(
    insertUiBuku = toInsertUiEvent()
)

fun Buku.toInsertUiEvent():InsertUiBuku = InsertUiBuku(
    id_buku = id_buku,
    judul = judul,
    penulis = penulis,
    kategori = kategori,
    status = status
)