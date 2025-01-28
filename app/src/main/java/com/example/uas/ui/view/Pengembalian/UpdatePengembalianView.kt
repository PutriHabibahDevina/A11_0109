package com.example.uas.ui.view.Pengembalian

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
import com.example.uas.ui.viewmodel.Pengembalian.UpdatePengembalianViewModel
import com.example.uas.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiEditPengembalian: DestinasiNavigasi {
    override val route = "editpengembalian"
    override val titleRes = "Edit Pengembalian"
    const val id_pengembalian = "id_pengembalian"
    val routeWithArgs = "$route/{$id_pengembalian}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePengembalianView(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdatePengembalianViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            CustomeTopAppBar(
                title = "Update Pengembalian",
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        }
    ) { innerPadding ->
        EntryBodyPengembalian(
            insertPengembalianUiState = viewModel.uiState,
            onPengembalianValueChange = viewModel::updateInsertPengembalianState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.editPengembalian()
                    navigateBack()
                }
            },
            modifier = modifier
                .padding(innerPadding)
                .fillMaxWidth()
        )
    }
}