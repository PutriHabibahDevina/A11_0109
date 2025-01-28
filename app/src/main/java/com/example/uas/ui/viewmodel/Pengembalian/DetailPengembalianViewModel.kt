package com.example.uas.ui.viewmodel.Pengembalian

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas.model.Pengembalian
import com.example.uas.repository.PengembalianRepository
import com.example.uas.ui.view.Pengembalian.DestinasiDetailPengembalian
import kotlinx.coroutines.launch
import okio.IOException

sealed class DetailpengembalianUiState{
    data class Success(val pengembalian: Pengembalian) : DetailpengembalianUiState()
    object Error : DetailpengembalianUiState()
    object Loading : DetailpengembalianUiState()
}

class DetailPengembalianViewModel(
    savedStateHandle: SavedStateHandle,
    private val pengembalianRepository: PengembalianRepository
) : ViewModel() {

    private val id_pengembalian: String = checkNotNull(savedStateHandle[DestinasiDetailPengembalian.id_pengembalian])
    var detailpengembalianUiState: DetailpengembalianUiState by mutableStateOf(DetailpengembalianUiState.Loading)
        private set

    init {
        getPengembalianById()
    }

    fun getPengembalianById(){
        viewModelScope.launch {
            detailpengembalianUiState = DetailpengembalianUiState.Loading
            detailpengembalianUiState = try {
                DetailpengembalianUiState.Success(pengembalian = pengembalianRepository.getPengembalianById(id_pengembalian))
            } catch (e: IOException) {
                DetailpengembalianUiState.Error
            }
        }
    }
}