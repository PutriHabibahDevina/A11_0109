package com.example.uas.ui.viewmodel.Buku

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas.model.Buku
import com.example.uas.repository.BukuRepository
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed class HomeUiState{
    data class Success(val buku: List<Buku>): HomeUiState()
    object Error: HomeUiState()
    object Loading: HomeUiState()
}

class HomeBukuViewModel (private val book: BukuRepository): ViewModel(){
    var bukuUIState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getBook()
    }

    fun getBook(){
        viewModelScope.launch {
            bukuUIState = HomeUiState.Loading
            bukuUIState = try {
                HomeUiState.Success(book.getBuku())
            }catch (e:IOException){
                HomeUiState.Error
            }catch (e: HttpException){
                HomeUiState.Error
            }
        }
    }

    fun deleteBook(id_buku:String){
        viewModelScope.launch {
            try {
                book.deleteBuku(id_buku)
            }catch (e: IOException){
                HomeUiState.Error
            }catch (e:HttpException){
                HomeUiState.Error
            }
        }
    }
}