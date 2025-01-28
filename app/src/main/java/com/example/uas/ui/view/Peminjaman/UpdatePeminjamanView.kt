package com.example.uas.ui.view.Peminjaman

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uas.ui.customwidget.CustomeTopAppBar
import com.example.uas.ui.navigation.DestinasiNavigasi
import com.example.uas.ui.viewmodel.Peminjaman.InsertUiPeminjaman
import com.example.uas.ui.viewmodel.Peminjaman.UpdatePeminjamanViewModel
import com.example.uas.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiEditPeminjaman: DestinasiNavigasi {
    override val route = "editpeminjaman"
    override val titleRes = "Edit Peminjaman"
    const val id_peminjaman = "id_peminjaman"
    val routeWithArgs = "$route/{$id_peminjaman}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePeminjamanView(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdatePeminjamanViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            CustomeTopAppBar(
                title = "Update Peminjaman",
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        }
    ) { innerPadding ->
        EntryBodyPeminjaman(
            insertPeminjamanUiState = viewModel.uiState.insertUiPeminjaman,
            onPeminjamanValueChange = viewModel::updateInsertPeminjamanState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.editPeminjaman()
                    navigateBack()
                }
            },
            modifier = modifier
                .padding(innerPadding)
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBodyPeminjaman(
    insertPeminjamanUiState: InsertUiPeminjaman,
    onPeminjamanValueChange: (InsertUiPeminjaman) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = insertPeminjamanUiState.id_peminjaman,
            onValueChange = { newValue ->
                onPeminjamanValueChange(insertPeminjamanUiState.copy(id_peminjaman = newValue))
            },
            label = { Text("ID Peminjaman") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = insertPeminjamanUiState.id_buku,
            onValueChange = { newValue ->
                onPeminjamanValueChange(insertPeminjamanUiState.copy(id_buku = newValue))
            },
            label = { Text("ID Buku") },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )
        OutlinedTextField(
            value = insertPeminjamanUiState.id_anggota,
            onValueChange = { newValue ->
                onPeminjamanValueChange(insertPeminjamanUiState.copy(id_anggota = newValue))
            },
            label = { Text("ID Anggota") },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )
        OutlinedTextField(
            value = insertPeminjamanUiState.tanggal_peminjaman,
            onValueChange = { newValue ->
                onPeminjamanValueChange(insertPeminjamanUiState.copy(tanggal_peminjaman = newValue))
            },
            label = { Text("ID Anggota") },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )
        OutlinedTextField(
            value = insertPeminjamanUiState.tanggal_pengembalian,
            onValueChange = { newValue ->
                onPeminjamanValueChange(insertPeminjamanUiState.copy(tanggal_pengembalian = newValue))
            },
            label = { Text("ID Anggota") },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )

        Button(
            onClick = onSaveClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Save")
        }
    }
}