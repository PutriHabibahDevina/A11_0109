package com.example.uas.ui.viewmodel.Pengembalian

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas.model.Pengembalian
import com.example.uas.repository.PengembalianRepository
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed class HomePengembalianUiState{
    data class Success(val pengembalian: List<Pengembalian>): HomePengembalianUiState()
    object Error: HomePengembalianUiState()
    object Loading: HomePengembalianUiState()
}

class HomePengembalianViewModel (private val kembali: PengembalianRepository): ViewModel(){
    var pengembalianUIState: HomePengembalianUiState by mutableStateOf(HomePengembalianUiState.Loading)
        private set

    init {
        getKembali()
    }

    fun getKembali(){
        viewModelScope.launch {
            pengembalianUIState = HomePengembalianUiState.Loading
            pengembalianUIState = try {
                HomePengembalianUiState.Success(kembali.getPengembalian())
            }catch (e:IOException){
                HomePengembalianUiState.Error
            }catch (e: HttpException){
                HomePengembalianUiState.Error
            }
        }
    }
}