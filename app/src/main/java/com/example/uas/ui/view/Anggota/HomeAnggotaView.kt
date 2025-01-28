package com.example.uas.ui.view.Anggota

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
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
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
import com.example.uas.model.Anggota
import com.example.uas.ui.customwidget.CustomeTopAppBar
import com.example.uas.ui.navigation.DestinasiNavigasi
import com.example.uas.ui.viewmodel.Anggota.HomeAnggotaUiState
import com.example.uas.ui.viewmodel.Anggota.HomeAnggotaViewModel
import com.example.uas.ui.viewmodel.PenyediaViewModel

object DestinasiHomeAnggota: DestinasiNavigasi {
    override val route = "home_anggota"
    override val titleRes = "Home Anggota"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenAnggota(
    navigateToItemEntry:()->Unit,
    modifier: Modifier = Modifier,
    onDetailClick:(String)->Unit = {},
    viewModel: HomeAnggotaViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomeTopAppBar(
                title = DestinasiHomeAnggota.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getMember()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Pengembalian")
            }
        }
    ) { innerpadding->
        HomeStatus(
            homeUiState = viewModel.anggotaUIState,
            retryAction = {viewModel.getMember()}, modifier = Modifier.padding(innerpadding),
            onDetailClick = onDetailClick, onDeleteCLick = {
                viewModel.deleteMember(it.id_anggota)
                viewModel.getMember()
            }
        )
    }
}

@Composable
fun HomeStatus(
    homeUiState: HomeAnggotaUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteCLick: (Anggota) -> Unit = {},
    onDetailClick: (String) -> Unit
){
    when(homeUiState){
        is HomeAnggotaUiState.Loading-> OnLoading(modifier = modifier.fillMaxSize())
        is HomeAnggotaUiState.Success->
            if (homeUiState.anggota.isEmpty()){
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Text(text = "Tidak ada data Anggota")
                }
            }else{
                AnggotaLayout(
                    anggota = homeUiState.anggota, modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.id_anggota)
                    },
                    onDeleteCLick = {
                        onDeleteCLick(it)
                    }
                )
            }
        is HomeAnggotaUiState.Error-> OnError(retryAction,modifier = modifier.fillMaxSize())
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
fun AnggotaLayout(
    anggota: List<Anggota>,
    modifier: Modifier = Modifier,
    onDetailClick: (Anggota)->Unit,
    onDeleteCLick: (Anggota) -> Unit = {}
){
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(anggota){anggota->
            AnggotaCard(
                anggota = anggota,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(anggota) },
                onDeleteCLick = {
                    onDeleteCLick(anggota)
                }
            )
        }
    }
}

@Composable
fun AnggotaCard(
    anggota: Anggota,
    modifier: Modifier = Modifier,
    onDeleteCLick:(Anggota)-> Unit ={}
){
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Konfirmasi") },
            text = { Text("Apakah Anda yakin ingin menghapus anggota ${anggota.nama}?") },
            confirmButton = {
                Button(
                    onClick = {
                        onDeleteCLick(anggota)
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
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = anggota.nama,
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
                    text = anggota.id_anggota,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.Email, contentDescription = "")
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = anggota.email,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.Call, contentDescription = "")
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = anggota.nomor_telepon,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}