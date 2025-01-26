package com.example.uas.ui.view.Peminjaman

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
import com.example.uas.model.Peminjaman
import com.example.uas.ui.customwidget.CustomeTopAppBar
import com.example.uas.ui.viewmodel.Peminjaman.DetailPeminjamanViewModel
import com.example.uas.ui.viewmodel.Peminjaman.DetailpeminjamanUiState

object DestinasiDetailPeminjaman : DestinasiNavigasi {
    override val route = "id_peminjaman"
    override val titleRes = "Detail Peminjaman"
    const val id_peminjaman = "id_peminjaman"
    val routeWithArgs = "$route/{$id_peminjaman}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPeminjamanView(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    onEditClick: (String) -> Unit,
    detailpeminjamanViewModel: DetailPeminjamanViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomeTopAppBar(
                title = DestinasiDetailPeminjaman.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val id_peminjaman = (detailpeminjamanViewModel.detailpeminjamanUiState as? DetailpeminjamanUiState.Success)?.peminjaman?.id_peminjaman
                    if (id_peminjaman != null) onEditClick(id_peminjaman)
                },
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Peminjaman",
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
                peminjamanUiState = detailpeminjamanViewModel.detailpeminjamanUiState,
                retryAction = { detailpeminjamanViewModel.getPeminjamanById() },
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
    peminjamanUiState: DetailpeminjamanUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (peminjamanUiState) {
        is DetailpeminjamanUiState.Success -> {
            DetailCard(
                peminjaman = peminjamanUiState.peminjaman,
                modifier = modifier.padding(16.dp)
            )
        }

        is DetailpeminjamanUiState.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is DetailpeminjamanUiState.Error -> {
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
    peminjaman: Peminjaman,
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
            ComponentDetailPeminjaman(judul = "ID Peminjaman", isinya = peminjaman.id_peminjaman)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailPeminjaman(judul = "ID Buku", isinya = peminjaman.id_buku)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailPeminjaman(judul = "ID Anggota", isinya = peminjaman.id_anggota)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailPeminjaman(judul = "Tanggal Peminjaman", isinya = peminjaman.tanggal_peminjaman)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailPeminjaman(judul = "Tanggal Pengembalian", isinya = peminjaman.tanggal_pengembalian)
        }
    }
}

@Composable
fun ComponentDetailPeminjaman(
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