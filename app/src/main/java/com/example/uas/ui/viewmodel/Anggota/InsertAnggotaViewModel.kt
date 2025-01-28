package com.example.uas.ui.viewmodel.Anggota

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas.model.Anggota
import com.example.uas.repository.AnggotaRepository
import kotlinx.coroutines.launch

class InsertAnggotaViewModel (private val member: AnggotaRepository):ViewModel(){
    var uiState by mutableStateOf(InsertAnggotaUiState())
        private set

    fun updateInsertAnggotaState(insertUiAngota: InsertUiAnggota){
        uiState = InsertAnggotaUiState(insertUiAnggota = insertUiAngota)
    }

    suspend fun insertAnggota(){
        viewModelScope.launch {
            try {
                member.insertAnggota(uiState.insertUiAnggota.toAnggota())
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}


data class InsertAnggotaUiState(
    val insertUiAnggota : InsertUiAnggota = InsertUiAnggota()
)

data class InsertUiAnggota(
    val id_anggota: String = "",
    val nama: String = "",
    val email: String = "",
    val nomor_telepon: String = ""
)

fun InsertUiAnggota.toAnggota(): Anggota = Anggota(
    id_anggota = id_anggota,
    nama = nama,
    email = email,
    nomor_telepon = nomor_telepon
)

fun Anggota.toUiStateAnggota():InsertAnggotaUiState = InsertAnggotaUiState(
    insertUiAnggota = toInsertUiAnggota()
)

fun Anggota.toInsertUiAnggota():InsertUiAnggota = InsertUiAnggota(
    id_anggota = id_anggota,
    nama = nama,
    email = email,
    nomor_telepon = nomor_telepon
)