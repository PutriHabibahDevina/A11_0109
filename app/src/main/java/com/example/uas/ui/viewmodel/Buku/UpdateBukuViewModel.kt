package com.example.uas.ui.viewmodel.Buku

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas.repository.BukuRepository
import com.example.uas.ui.view.Buku.DestinasiEditBuku
import kotlinx.coroutines.launch

class UpdateBukuViewModel(
    savedStateHandle: SavedStateHandle,
    private val bukuRepository: BukuRepository
) : ViewModel() {

    var uiState by mutableStateOf(InsertUiState())
        private set

    val id_buku: String = checkNotNull(savedStateHandle[DestinasiEditBuku.id_buku])

    init {
        viewModelScope.launch {
            uiState = bukuRepository.getBukuById(id_buku).toUiStateBuku()
        }
    }

    fun updateInsertBukuState(insertbukuUiEvent: InsertUiBuku) {
        uiState = InsertUiState(insertUiBuku = insertbukuUiEvent)
    }

    suspend fun editBuku(){
            try {
                bukuRepository.updateBuku(id_buku, uiState.insertUiBuku.toBuku())
            } catch (e: Exception) {
                e.printStackTrace()
            }
    }
}