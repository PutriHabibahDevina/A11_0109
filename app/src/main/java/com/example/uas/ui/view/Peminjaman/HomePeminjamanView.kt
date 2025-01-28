package com.example.uas.ui.view.Peminjaman

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uas.R
import com.example.uas.model.Peminjaman
import com.example.uas.ui.customwidget.CustomeTopAppBar
import com.example.uas.ui.navigation.DestinasiNavigasi
import com.example.uas.ui.viewmodel.Peminjaman.HomePeminjamanUiState
import com.example.uas.ui.viewmodel.Peminjaman.HomePeminjamanViewModel
import com.example.uas.ui.viewmodel.PenyediaViewModel

object DestinasiHomePeminjaman: DestinasiNavigasi {
    override val route = "home peminjaman"
    override val titleRes = "Home Peminjaman"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenPeminjaman(
    navigateToItemEntry:()->Unit,
    modifier: Modifier = Modifier,
    onDetailClick:(String)->Unit = {},
    viewModel: HomePeminjamanViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomeTopAppBar(
                title = DestinasiHomePeminjaman.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getBorrow()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Peminjaman")
            }
        }
    ) { innerpadding->
        HomeStatus(
            homeUiState = viewModel.peminjamanUIState,
            retryAction = {viewModel.getBorrow()}, modifier = Modifier.padding(innerpadding),
            onDetailClick = onDetailClick, onDeleteCLick = {
                viewModel.deleteBorrow(it.id_peminjaman)
                viewModel.getBorrow()
            }
        )
    }
}

@Composable
fun HomeStatus(
    homeUiState: HomePeminjamanUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteCLick: (Peminjaman) -> Unit = {},
    onDetailClick: (String) -> Unit
){
    when(homeUiState){
        is HomePeminjamanUiState.Loading-> OnLoading(modifier = modifier.fillMaxSize())
        is HomePeminjamanUiState.Success->
            if (homeUiState.peminjaman.isEmpty()){
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Text(text = "Tidak ada data Peminjaman")
                }
            }else{
                PeminjamanLayout(
                    peminjaman = homeUiState.peminjaman, modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.id_peminjaman)
                    },
                    onDeleteCLick = {
                        onDeleteCLick(it)
                    }
                )
            }
        is HomePeminjamanUiState.Error-> OnError(retryAction,modifier = modifier.fillMaxSize())
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier){
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun OnError(retryAction:()->Unit, modifier: Modifier = Modifier){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}


@Composable
fun PeminjamanLayout(
    peminjaman: List<Peminjaman>,
    modifier: Modifier = Modifier,
    onDetailClick: (Peminjaman)->Unit,
    onDeleteCLick: (Peminjaman) -> Unit = {}
){
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(peminjaman){peminjaman->
            PeminjamanCard(
                peminjaman = peminjaman,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(peminjaman) },
                onDeleteCLick = {
                    onDeleteCLick(peminjaman)
                }
            )
        }
    }
}

@Composable
fun PeminjamanCard(
    peminjaman: Peminjaman,
    modifier: Modifier = Modifier,
    onDeleteCLick:(Peminjaman)-> Unit ={}
){
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Konfirmasi") },
            text = { Text("Apakah Anda yakin ingin menghapus peminjaman ${peminjaman.id_peminjaman}?") },
            confirmButton = {
                Button(
                    onClick = {
                        onDeleteCLick(peminjaman)
                        showDialog = false
                    }
                ) {
                    Text("Ya")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Batal")
                }
            }
        )
    }

    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = peminjaman.id_peminjaman,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = {showDialog = true}) { // Menampilkan dialog saat tombol delete ditekan
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null
                    )
                }
                Text(
                    text = peminjaman.id_buku,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.Person, contentDescription = "")
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = peminjaman.id_anggota,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.DateRange, contentDescription = "")
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = peminjaman.tanggal_peminjaman,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.CheckCircle, contentDescription = "")
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = peminjaman.tanggal_pengembalian,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}