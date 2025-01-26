package com.example.uas.ui.viewmodel.Anggota

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas.model.Anggota
import com.example.uas.repository.AnggotaRepository
import com.example.uas.ui.view.Anggota.DestinasiDetailAnggota
import kotlinx.coroutines.launch
import okio.IOException

sealed class DetailanggotaUiState{
    data class Success(val anggota: Anggota) : DetailanggotaUiState()
    object Error : DetailanggotaUiState()
    object Loading : DetailanggotaUiState()
}

class DetailAnggotaViewModel(
    savedStateHandle: SavedStateHandle,
    private val anggotaRepository: AnggotaRepository
) : ViewModel() {

    private val id_anggota: String = checkNotNull(savedStateHandle[DestinasiDetailAnggota.id_anggota])
    var detailanggotaUiState: DetailanggotaUiState by mutableStateOf(DetailanggotaUiState.Loading)
        private set

    init {
        getAnggotaById()
    }

    fun getAnggotaById(){
        viewModelScope.launch {
            detailanggotaUiState = DetailanggotaUiState.Loading
            detailanggotaUiState = try {
                DetailanggotaUiState.Success(anggota = anggotaRepository.getAnggotaById(id_anggota))
            } catch (e: IOException) {
                DetailanggotaUiState.Error
            }
        }
    }
}