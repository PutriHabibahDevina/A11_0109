package com.example.uas.ui.viewmodel.Anggota

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas.repository.AnggotaRepository
import com.example.uas.ui.view.Anggota.DestinasiEditAnggota
import kotlinx.coroutines.launch

class UpdateAnggotaViewModel(
    savedStateHandle: SavedStateHandle,
    private val AnggotaRepository: AnggotaRepository
) : ViewModel() {

    var uiState by mutableStateOf(InsertUiState())
        private set

    val id_anggota: String = checkNotNull(savedStateHandle[DestinasiEditAnggota.id_anggota])

    init {
        viewModelScope.launch {
            uiState = AnggotaRepository.getAnggotaById(id_anggota).toUiStateAnggota()
        }
    }

    fun updateInsertAnggotaState(insertanggotaUiAnggota: InsertUiAnggota) {
        uiState = InsertUiState(insertUiAnggota = insertanggotaUiAnggota)
    }

    suspend fun editAnggota(){
        try {
            AnggotaRepository.updateAnggota(id_anggota, uiState.insertUiAnggota.toAnggota())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}