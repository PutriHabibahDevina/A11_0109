package com.example.uas.ui.view.Peminjaman

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uas.ui.customwidget.CustomeTopAppBar
import com.example.uas.ui.navigation.DestinasiNavigasi
import com.example.uas.ui.viewmodel.Peminjaman.InsertPeminjamanViewModel
import com.example.uas.ui.viewmodel.Peminjaman.InsertUiPeminjaman
import com.example.uas.ui.viewmodel.Peminjaman.InsertUiState
import com.example.uas.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiEntryPeminjaman: DestinasiNavigasi {
    override val route = "item_entrypeminjaman"
    override val titleRes = "Entry Peminjaman"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryPeminjamanScreen(
    navigateBack:()->Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertPeminjamanViewModel = viewModel(factory = PenyediaViewModel.Factory)

){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomeTopAppBar(
                title = DestinasiEntryPeminjaman.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerpadding->
        EntryBodyPeminjaman(
            insertPeminjamanUiState = viewModel.uiState,
            onPeminjamanValueChange = viewModel::updateInsertPeminjamanState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertPeminjaman()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerpadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBodyPeminjaman(
    insertPeminjamanUiState: InsertUiState,
    onPeminjamanValueChange: (InsertUiPeminjaman)->Unit,
    onSaveClick:()->Unit,
    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = Modifier.padding(12.dp)
    ) {
        FormInputPeminjaman(
            insertUiPeminjaman = insertPeminjamanUiState.insertUiPeminjaman,
            onValueChange = onPeminjamanValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputPeminjaman(
    insertUiPeminjaman: InsertUiPeminjaman,
    modifier: Modifier = Modifier,
    onValueChange:(InsertUiPeminjaman)->Unit = {},
    enabled: Boolean = true
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertUiPeminjaman.id_peminjaman,
            onValueChange = {onValueChange(insertUiPeminjaman.copy(id_peminjaman = it))},
            label = { Text("ID Peminjaman") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiPeminjaman.id_peminjaman,
            onValueChange = {onValueChange(insertUiPeminjaman.copy(id_peminjaman = it))},
            label = { Text("ID Peminjaman") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiPeminjaman.id_buku,
            onValueChange = {onValueChange(insertUiPeminjaman.copy(id_buku = it))},
            label = { Text("ID Buku") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiPeminjaman.id_anggota,
            onValueChange = {onValueChange(insertUiPeminjaman.copy(id_anggota = it))},
            label = { Text("ID Anggota") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiPeminjaman.tanggal_peminjaman,
            onValueChange = {onValueChange(insertUiPeminjaman.copy(tanggal_peminjaman = it))},
            label = { Text("Tanggal Peminjaman") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiPeminjaman.tanggal_pengembalian,
            onValueChange = {onValueChange(insertUiPeminjaman.copy(tanggal_pengembalian = it))},
            label = { Text("Tanggal Pengembalian") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        if (enabled){
            Text(
                text = "Isi Semua Data!",
                modifier = Modifier.padding(12.dp)
            )
        }
        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )
    }
}