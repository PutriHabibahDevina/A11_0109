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

sealed class HomeBukuUiState{
    data class Success(val buku: List<Buku>): HomeBukuUiState()
    object Error: HomeBukuUiState()
    object Loading: HomeBukuUiState()
}

class HomeBukuViewModel (private val book: BukuRepository): ViewModel(){
    var bukuUIState: HomeBukuUiState by mutableStateOf(HomeBukuUiState.Loading)
        private set

    init {
        getBook()
    }

    fun getBook(){
        viewModelScope.launch {
            bukuUIState = HomeBukuUiState.Loading
            bukuUIState = try {
                HomeBukuUiState.Success(book.getBuku())
            }catch (e:IOException){
                HomeBukuUiState.Error
            }catch (e: HttpException){
                HomeBukuUiState.Error
            }
        }
    }

    fun deleteBook(id_buku:String){
        viewModelScope.launch {
            try {
                book.deleteBuku(id_buku)
            }catch (e: IOException){
                HomeBukuUiState.Error
            }catch (e:HttpException){
                HomeBukuUiState.Error
            }
        }
    }
}