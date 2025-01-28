package com.example.uas.ui.view.Pengembalian

import com.example.uas.ui.navigation.DestinasiNavigasi
import com.example.uas.ui.viewmodel.PenyediaViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uas.model.Pengembalian
import com.example.uas.ui.customwidget.CustomeTopAppBar
import com.example.uas.ui.viewmodel.Pengembalian.DetailPengembalianViewModel
import com.example.uas.ui.viewmodel.Pengembalian.DetailpengembalianUiState

object DestinasiDetailPengembalian : DestinasiNavigasi {
    override val route = "id_pengembalian"
    override val titleRes = "Detail Pengembalian"
    const val id_pengembalian = "id_pengembalian"
    val routeWithArgs = "$route/{$id_pengembalian}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPengembalianView(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    onEditClick: (String) -> Unit,
    detailpengembalianViewModel: DetailPengembalianViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomeTopAppBar(
                title = DestinasiDetailPengembalian.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val id_pengembalian = (detailpengembalianViewModel.detailpengembalianUiState as? DetailpengembalianUiState.Success)?.pengembalian?.id_pengembalian
                    if (id_pengembalian != null) onEditClick(id_pengembalian)
                },
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Anggota",
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding).offset(y = (-70).dp)
        ) {
            DetailStatus(
                pengembalianUiState = detailpengembalianViewModel.detailpengembalianUiState,
                retryAction = { detailpengembalianViewModel.getPengembalianById() },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun DetailStatus(
    pengembalianUiState: DetailpengembalianUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (pengembalianUiState) {
        is DetailpengembalianUiState.Success -> {
            DetailCard(
                pengembalian = pengembalianUiState.pengembalian,
                modifier = modifier.padding(16.dp)
            )
        }

        is DetailpengembalianUiState.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is DetailpengembalianUiState.Error -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Terjadi kesalahan.")
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = retryAction) {
                        Text(text = "Coba Lagi")
                    }
                }
            }
        }
    }
}

@Composable
fun DetailCard(
    pengembalian: Pengembalian,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ComponentDetailPengembalian(judul = "ID Pengembalian", isinya = pengembalian.id_pengembalian)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailPengembalian(judul = "ID Peminjaman", isinya = pengembalian.id_peminjaman)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailPengembalian(judul = "Tanggal Dikembalikan", isinya = pengembalian.tanggal_dikembalikan)
        }
    }
}

@Composable
fun ComponentDetailPengembalian(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$judul:",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Text(
            text = isinya,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}