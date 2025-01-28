package com.example.uas.ui.view.Peminjaman

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uas.model.Anggota
import com.example.uas.model.Buku
import com.example.uas.ui.customwidget.CustomeTopAppBar
import com.example.uas.ui.navigation.DestinasiNavigasi
import com.example.uas.ui.viewmodel.Anggota.HomeAnggotaUiState
import com.example.uas.ui.viewmodel.Anggota.HomeAnggotaViewModel
import com.example.uas.ui.viewmodel.Buku.HomeBukuUiState
import com.example.uas.ui.viewmodel.Buku.HomeBukuViewModel
import com.example.uas.ui.viewmodel.Peminjaman.InsertPeminjamanUiState
import com.example.uas.ui.viewmodel.Peminjaman.InsertPeminjamanViewModel
import com.example.uas.ui.viewmodel.Peminjaman.InsertUiPeminjaman
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
    viewModel: InsertPeminjamanViewModel = viewModel(factory = PenyediaViewModel.Factory),
    viewModelAnggota: HomeAnggotaViewModel = viewModel(factory = PenyediaViewModel.Factory),
    viewModelBuku: HomeBukuViewModel = viewModel(factory = PenyediaViewModel.Factory),
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val anggotaUiState = viewModelAnggota.anggotaUIState
    val bukuUiState = viewModelBuku.bukuUIState

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
            anggota = anggotaUiState,
            buku = bukuUiState,
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
    insertPeminjamanUiState: InsertPeminjamanUiState,
    onPeminjamanValueChange: (InsertUiPeminjaman)->Unit,
    onSaveClick:()->Unit,
    modifier: Modifier = Modifier,
    anggota: HomeAnggotaUiState,
    buku: HomeBukuUiState
){
    when(buku){
        is HomeBukuUiState.Success -> {
            when (anggota) {
                is HomeAnggotaUiState.Success -> Column(
                    verticalArrangement = Arrangement.spacedBy(18.dp),
                    modifier = Modifier.padding(12.dp)
                ) {
                    FormInputPeminjaman(
                        insertUiPeminjaman = insertPeminjamanUiState.insertUiPeminjaman,
                        onValueChange = onPeminjamanValueChange,
                        modifier = Modifier.fillMaxWidth(),
                        anggotaList = anggota.anggota,
                        bukuList = buku.buku
                    )
                    Button(
                        onClick = onSaveClick,
                        shape = MaterialTheme.shapes.small,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Simpan")
                    }
                }
                HomeAnggotaUiState.Error -> TODO()
                HomeAnggotaUiState.Loading -> TODO()
            }
        }
        HomeBukuUiState.Error -> TODO()
        HomeBukuUiState.Loading -> TODO()
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputPeminjaman(
    insertUiPeminjaman: InsertUiPeminjaman,
    anggotaList: List<Anggota>,
    bukuList: List<Buku>,
    modifier: Modifier = Modifier,
    onValueChange:(InsertUiPeminjaman)->Unit = {},
    enabled: Boolean = true
){
    var selectedAnggota by remember { mutableStateOf(anggotaList.find { it.id_anggota == insertUiPeminjaman.id_anggota } ?: anggotaList.firstOrNull()) }
    var selectedBuku by remember { mutableStateOf(bukuList.find { it.id_buku == insertUiPeminjaman.id_buku } ?: bukuList.firstOrNull()) }

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

        DropdownMenuFieldWithObject(
            label = "ID Anggota",
            options = anggotaList,
            selectedOption = selectedAnggota,
            displayText = {it.id_anggota},
            onOptionSelected = {
                selectedAnggota = it
                onValueChange(insertUiPeminjaman.copy(id_anggota = it.id_anggota))
            }
        )
        DropdownMenuFieldWithObject(
            label = "ID Buku",
            options = bukuList,
            selectedOption = selectedBuku,
            displayText = {it.id_buku},
            onOptionSelected = {
                selectedBuku = it
                onValueChange(insertUiPeminjaman.copy(id_buku = it.id_buku))
            }
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

@Composable
fun <T> DropdownMenuFieldWithObject(
    label: String,
    options: List<T>,
    selectedOption: T?,
    displayText: (T)-> String,
    onOptionSelected: (T)-> Unit
){
    var expanded by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(
            value = selectedOption?.let {displayText(it)} ?: "",
            onValueChange = {},
            label = {Text(label)},
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = {expanded =! expanded}) {
                    Icon(
                        imageVector = if(expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = null
                    )
                }
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        onOptionSelected(option)
                    },
                    text = { Text(displayText(option)) }
                )
            }
        }
    }
}