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

sealed class HomeUiState{
    data class Success(val pengembalian: List<Pengembalian>): HomeUiState()
    object Error: HomeUiState()
    object Loading: HomeUiState()
}

class HomePengembalianViewModel (private val kembali: PengembalianRepository): ViewModel(){
    var pengembalianUIState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getKembali()
    }

    fun getKembali(){
        viewModelScope.launch {
            pengembalianUIState = HomeUiState.Loading
            pengembalianUIState = try {
                HomeUiState.Success(kembali.getPengembalian())
            }catch (e:IOException){
                HomeUiState.Error
            }catch (e: HttpException){
                HomeUiState.Error
            }
        }
    }
}