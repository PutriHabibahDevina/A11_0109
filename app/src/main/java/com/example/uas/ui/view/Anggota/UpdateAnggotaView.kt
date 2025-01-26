package com.example.uas.ui.view.Anggota

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
import com.example.uas.ui.viewmodel.Anggota.UpdateAnggotaViewModel
import com.example.uas.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiEditAnggota: DestinasiNavigasi {
    override val route = "editanggota"
    override val titleRes = "Edit Anggota"
    const val id_anggota = "id_anggota"
    val routeWithArgs = "$route/{$id_anggota}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateAnggotaView(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateAnggotaViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            CustomeTopAppBar(
                title = "Update Anggota",
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        }
    ) { innerPadding ->
        EntryBodyAnggota(
            insertAnggotaUiState = viewModel.uiState,
            onAnggotaValueChange = viewModel::updateInsertAnggotaState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.editAnggota()
                    navigateBack()
                }
            },
            modifier = modifier
                .padding(innerPadding)
                .fillMaxWidth()
        )
    }
}