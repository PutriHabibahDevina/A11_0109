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

sealed class HomeAnggotaUiState{
    data class Success(val anggota: List<Anggota>): HomeAnggotaUiState()
    object Error: HomeAnggotaUiState()
    object Loading: HomeAnggotaUiState()
}

class HomeAnggotaViewModel (private val member: AnggotaRepository): ViewModel(){
    var anggotaUIState: HomeAnggotaUiState by mutableStateOf(HomeAnggotaUiState.Loading)
        private set

    init {
        getMember()
    }

    fun getMember(){
        viewModelScope.launch {
            anggotaUIState = HomeAnggotaUiState.Loading
            anggotaUIState = try {
                HomeAnggotaUiState.Success(member.getAnggota())
            }catch (e:IOException){
                HomeAnggotaUiState.Error
            }catch (e: HttpException){
                HomeAnggotaUiState.Error
            }
        }
    }

    fun deleteMember(id_anggota:String){
        viewModelScope.launch {
            try {
                member.deleteAnggota(id_anggota)
            }catch (e: IOException){
                HomeAnggotaUiState.Error
            }catch (e:HttpException){
                HomeAnggotaUiState.Error
            }
        }
    }
}