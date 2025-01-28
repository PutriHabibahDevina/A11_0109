package com.example.uas.ui.viewmodel.Pengembalian

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas.repository.PengembalianRepository
import com.example.uas.ui.view.Pengembalian.DestinasiEditPengembalian
import kotlinx.coroutines.launch

class UpdatePengembalianViewModel(
    savedStateHandle: SavedStateHandle,
    private val pengembalianRepository: PengembalianRepository
) : ViewModel() {

    var uiState by mutableStateOf(InsertUiState())
        private set

    val id_pengembalian: String = checkNotNull(savedStateHandle[DestinasiEditPengembalian.id_pengembalian])

    init {
        viewModelScope.launch {
            uiState = pengembalianRepository.getPengembalianById(id_pengembalian).toUiStatePengembalian()
        }
    }

    fun updateInsertPengembalianState(insertbukuUiPengembalian: InsertUiPengembalian) {
        uiState = InsertUiState(insertUiPengembalian = insertbukuUiPengembalian)
    }

    suspend fun editPengembalian(){
        try {
            pengembalianRepository.updatePengembalian(id_pengembalian, uiState.insertUiPengembalian.toPengembalian())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}