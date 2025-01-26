package com.example.uas.ui.viewmodel.Buku

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas.model.Buku
import com.example.uas.repository.BukuRepository
import com.example.uas.ui.view.Buku.DestinasiDetailBuku
import kotlinx.coroutines.launch
import okio.IOException

sealed class DetailbukuUiState{
    data class Success(val buku: Buku) : DetailbukuUiState()
    object Error : DetailbukuUiState()
    object Loading : DetailbukuUiState()
}

class DetailBukuViewModel(
    savedStateHandle: SavedStateHandle,
    private val bukuRepository: BukuRepository
) : ViewModel() {

    private val id_buku: String = checkNotNull(savedStateHandle[DestinasiDetailBuku.id_buku])
    var detailbukuUiState: DetailbukuUiState by mutableStateOf(DetailbukuUiState.Loading)
        private set

    init {
        getBukuById()
    }

    fun getBukuById(){
        viewModelScope.launch {
            detailbukuUiState = DetailbukuUiState.Loading
            detailbukuUiState = try {
                DetailbukuUiState.Success(buku = bukuRepository.getBukuById(id_buku))
            } catch (e: IOException) {
                DetailbukuUiState.Error
            }
        }
    }
}