package com.example.uas.ui.viewmodel.Peminjaman

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas.model.Peminjaman
import com.example.uas.repository.PeminjamanRepository
import com.example.uas.ui.view.Peminjaman.DestinasiDetailPeminjaman
import kotlinx.coroutines.launch
import okio.IOException

sealed class DetailpeminjamanUiState{
    data class Success(val peminjaman: Peminjaman) : DetailpeminjamanUiState()
    object Error : DetailpeminjamanUiState()
    object Loading : DetailpeminjamanUiState()
}

class DetailPeminjamanViewModel(
    savedStateHandle: SavedStateHandle,
    private val peminjamanRepository: PeminjamanRepository
) : ViewModel() {

    private val id_peminjaman: String = checkNotNull(savedStateHandle[DestinasiDetailPeminjaman.id_peminjaman])
    var detailpeminjamanUiState: DetailpeminjamanUiState by mutableStateOf(DetailpeminjamanUiState.Loading)
        private set

    init {
        getPeminjamanById()
    }

    fun getPeminjamanById(){
        viewModelScope.launch {
            detailpeminjamanUiState = DetailpeminjamanUiState.Loading
            detailpeminjamanUiState = try {
                DetailpeminjamanUiState.Success(peminjaman = peminjamanRepository.getPeminjamanById(id_peminjaman))
            } catch (e: IOException) {
                DetailpeminjamanUiState.Error
            }
        }
    }
}