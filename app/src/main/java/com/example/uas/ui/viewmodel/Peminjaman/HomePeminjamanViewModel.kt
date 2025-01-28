package com.example.uas.ui.viewmodel.Peminjaman

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas.model.Peminjaman
import com.example.uas.repository.PeminjamanRepository
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed class HomePeminjamanUiState{
    data class Success(val peminjaman: List<Peminjaman>): HomePeminjamanUiState()
    object Error: HomePeminjamanUiState()
    object Loading: HomePeminjamanUiState()
}

class HomePeminjamanViewModel (private val borrow: PeminjamanRepository): ViewModel(){
    var peminjamanUIState: HomePeminjamanUiState by mutableStateOf(HomePeminjamanUiState.Loading)
        private set

    init {
        getBorrow()
    }

    fun getBorrow(){
        viewModelScope.launch {
            peminjamanUIState = HomePeminjamanUiState.Loading
            peminjamanUIState = try {
                HomePeminjamanUiState.Success(borrow.getPeminjaman())
            }catch (e:IOException){
                HomePeminjamanUiState.Error
            }catch (e: HttpException){
                HomePeminjamanUiState.Error
            }
        }
    }

    fun deleteBorrow(id_peminjaman:String){
        viewModelScope.launch {
            try {
                borrow.deletePeminjaman(id_peminjaman)
            }catch (e: IOException){
                HomePeminjamanUiState.Error
            }catch (e:HttpException){
                HomePeminjamanUiState.Error
            }
        }
    }
}