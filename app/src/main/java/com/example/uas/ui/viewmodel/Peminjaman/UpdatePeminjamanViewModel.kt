package com.example.uas.ui.viewmodel.Peminjaman

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas.repository.PeminjamanRepository
import com.example.uas.ui.view.Peminjaman.DestinasiEditPeminjaman
import kotlinx.coroutines.launch

class UpdatePeminjamanViewModel(
    savedStateHandle: SavedStateHandle,
    private val peminjamanRepository: PeminjamanRepository
) : ViewModel() {

    var uiState by mutableStateOf(InsertPeminjamanUiState())
        private set

    val id_peminjaman: String = checkNotNull(savedStateHandle[DestinasiEditPeminjaman.id_peminjaman])

    init {
        viewModelScope.launch {
            uiState = peminjamanRepository.getPeminjamanById(id_peminjaman).toUiStatePeminjaman()
        }
    }

    fun updateInsertPeminjamanState(insertpeminjamanUiPeminjaman: InsertUiPeminjaman) {
        uiState = InsertPeminjamanUiState(insertUiPeminjaman = insertpeminjamanUiPeminjaman)
    }

    suspend fun editPeminjaman(){
        try {
            peminjamanRepository.updatePeminjaman(id_peminjaman, uiState.insertUiPeminjaman.toPeminjaman())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}