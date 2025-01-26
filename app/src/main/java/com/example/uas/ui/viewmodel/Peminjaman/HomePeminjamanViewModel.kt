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

sealed class HomeUiState{
    data class Success(val peminjaman: List<Peminjaman>): HomeUiState()
    object Error: HomeUiState()
    object Loading: HomeUiState()
}

class HomePeminjamanViewModel (private val borrow: PeminjamanRepository): ViewModel(){
    var peminjamanUIState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getBorrow()
    }

    fun getBorrow(){
        viewModelScope.launch {
            peminjamanUIState = HomeUiState.Loading
            peminjamanUIState = try {
                HomeUiState.Success(borrow.getPeminjaman())
            }catch (e:IOException){
                HomeUiState.Error
            }catch (e: HttpException){
                HomeUiState.Error
            }
        }
    }

    fun deleteBorrow(id_peminjaman:String){
        viewModelScope.launch {
            try {
                borrow.deletePeminjaman(id_peminjaman)
            }catch (e: IOException){
                HomeUiState.Error
            }catch (e:HttpException){
                HomeUiState.Error
            }
        }
    }
}