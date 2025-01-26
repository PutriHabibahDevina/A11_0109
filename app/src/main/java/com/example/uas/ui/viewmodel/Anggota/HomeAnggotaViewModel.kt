package com.example.uas.ui.viewmodel.Anggota

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas.model.Anggota
import com.example.uas.repository.AnggotaRepository
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed class HomeUiState{
    data class Success(val anggota: List<Anggota>): HomeUiState()
    object Error: HomeUiState()
    object Loading: HomeUiState()
}

class HomeAnggotaViewModel (private val member: AnggotaRepository): ViewModel(){
    var anggotaUIState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getMember()
    }

    fun getMember(){
        viewModelScope.launch {
            anggotaUIState = HomeUiState.Loading
            anggotaUIState = try {
                HomeUiState.Success(member.getAnggota())
            }catch (e:IOException){
                HomeUiState.Error
            }catch (e: HttpException){
                HomeUiState.Error
            }
        }
    }

    fun deleteMember(id_anggota:String){
        viewModelScope.launch {
            try {
                member.deleteAnggota(id_anggota)
            }catch (e: IOException){
                HomeUiState.Error
            }catch (e:HttpException){
                HomeUiState.Error
            }
        }
    }
}