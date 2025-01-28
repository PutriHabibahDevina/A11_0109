package com.example.uas.ui.viewmodel.Peminjaman

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas.model.Peminjaman
import com.example.uas.repository.PeminjamanRepository
import kotlinx.coroutines.launch

class InsertPeminjamanViewModel (private val borrow: PeminjamanRepository):ViewModel(){
    var uiState by mutableStateOf(InsertPeminjamanUiState())
        private set

    fun updateInsertPeminjamanState(insertUiPeminjaman: InsertUiPeminjaman){
        uiState = InsertPeminjamanUiState(insertUiPeminjaman = insertUiPeminjaman)
    }

    suspend fun insertPeminjaman(){
        viewModelScope.launch {
            try {
                borrow.insertPeminjaman(uiState.insertUiPeminjaman.toPeminjaman())
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}


data class InsertPeminjamanUiState(
    val insertUiPeminjaman : InsertUiPeminjaman = InsertUiPeminjaman()
)

data class InsertUiPeminjaman(
    val id_peminjaman: String = "",
    val id_buku: String = "",
    val id_anggota: String = "",
    val tanggal_peminjaman: String = "",
    val tanggal_pengembalian: String = ""
)

fun InsertUiPeminjaman.toPeminjaman(): Peminjaman = Peminjaman(
    id_peminjaman = id_peminjaman,
    id_buku = id_buku,
    id_anggota = id_anggota,
    tanggal_peminjaman = tanggal_peminjaman,
    tanggal_pengembalian = tanggal_pengembalian
)

fun Peminjaman.toUiStatePeminjaman():InsertPeminjamanUiState = InsertPeminjamanUiState(
    insertUiPeminjaman = toInsertUiPeminjaman()
)

fun Peminjaman.toInsertUiPeminjaman():InsertUiPeminjaman = InsertUiPeminjaman(
    id_peminjaman = id_peminjaman,
    id_buku = id_buku,
    id_anggota = id_anggota,
    tanggal_peminjaman = tanggal_peminjaman,
    tanggal_pengembalian = tanggal_pengembalian
)