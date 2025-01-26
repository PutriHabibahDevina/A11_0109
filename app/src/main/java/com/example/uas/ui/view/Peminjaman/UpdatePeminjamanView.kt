package com.example.uas.ui.view.Peminjaman

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uas.ui.customwidget.CustomeTopAppBar
import com.example.uas.ui.navigation.DestinasiNavigasi
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
            insertPeminjamanUiState = viewModel.uiState,
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