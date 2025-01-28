package com.example.uas.ui.view.Pengembalian

import com.example.uas.ui.viewmodel.Pengembalian.InsertPengembalianViewModel
import com.example.uas.ui.viewmodel.Pengembalian.InsertUiPengembalian
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
import com.example.uas.ui.viewmodel.Pengembalian.InsertPengembalianUiState
import com.example.uas.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiEntryPengembalian: DestinasiNavigasi {
    override val route = "item_entrypengembalian"
    override val titleRes = "Entry Pengembalian"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryPengembalianScreen(
    navigateBack:()->Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertPengembalianViewModel = viewModel(factory = PenyediaViewModel.Factory)

){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomeTopAppBar(
                title = DestinasiEntryPengembalian.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerpadding->
        EntryBodyPengembalian(
            insertPengembalianUiState = viewModel.uiState,
            onPengembalianValueChange = viewModel::updateInsertPengembalianState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertPengembalian()
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
fun EntryBodyPengembalian(
    insertPengembalianUiState: InsertPengembalianUiState,
    onPengembalianValueChange: (InsertUiPengembalian)->Unit,
    onSaveClick:()->Unit,
    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = Modifier.padding(12.dp)
    ) {
        FormInputPengembalian(
            insertUiPengembalian = insertPengembalianUiState.insertUiPengembalian,
            onValueChange = onPengembalianValueChange,
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
fun FormInputPengembalian(
    insertUiPengembalian: InsertUiPengembalian,
    modifier: Modifier = Modifier,
    onValueChange:(InsertUiPengembalian)->Unit = {},
    enabled: Boolean = true
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertUiPengembalian.id_pengembalian,
            onValueChange = {onValueChange(insertUiPengembalian.copy(id_pengembalian = it))},
            label = { Text("ID Pengembalian") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiPengembalian.id_pengembalian,
            onValueChange = {onValueChange(insertUiPengembalian.copy(id_pengembalian = it))},
            label = { Text("ID Pengembalian") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiPengembalian.id_peminjaman,
            onValueChange = {onValueChange(insertUiPengembalian.copy(id_peminjaman = it))},
            label = { Text("ID Peminjaman") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiPengembalian.tanggal_dikembalikan,
            onValueChange = {onValueChange(insertUiPengembalian.copy(tanggal_dikembalikan = it))},
            label = { Text("Tanggal Dikembalikan") },
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