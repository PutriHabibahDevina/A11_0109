package com.example.uas.ui.viewmodel.Pengembalian

import com.example.uas.model.Pengembalian
import com.example.uas.repository.PengembalianRepository
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class InsertPengembalianViewModel (private val kembali: PengembalianRepository):ViewModel(){
    var uiState by mutableStateOf(InsertPengembalianUiState())
        private set

    fun updateInsertPengembalianState(insertUiPengembalian: InsertUiPengembalian){
        uiState = InsertPengembalianUiState(insertUiPengembalian = insertUiPengembalian)
    }

    suspend fun insertPengembalian(){
        viewModelScope.launch {
            try {
                kembali.insertPengembalian(uiState.insertUiPengembalian.toPengembalian())
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}


data class InsertPengembalianUiState(
    val insertUiPengembalian : InsertUiPengembalian = InsertUiPengembalian()
)

data class InsertUiPengembalian(
    val id_pengembalian: String = "",
    val id_peminjaman: String = "",
    val tanggal_dikembalikan: String = ""
)

fun InsertUiPengembalian.toPengembalian(): Pengembalian = Pengembalian(
    id_pengembalian = id_pengembalian,
    id_peminjaman = id_peminjaman,
    tanggal_dikembalikan = tanggal_dikembalikan
)

fun Pengembalian.toUiStatePengembalian():InsertPengembalianUiState = InsertPengembalianUiState(
    insertUiPengembalian = toInsertUiPengembalian()
)

fun Pengembalian.toInsertUiPengembalian():InsertUiPengembalian = InsertUiPengembalian(
    id_pengembalian = id_pengembalian,
    id_peminjaman = id_peminjaman,
    tanggal_dikembalikan = tanggal_dikembalikan
)