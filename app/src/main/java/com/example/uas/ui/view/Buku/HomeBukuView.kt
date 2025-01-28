package com.example.uas.ui.view.Buku

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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
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
import androidx.compose.material3.TextField
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uas.R
import com.example.uas.model.Buku
import com.example.uas.ui.customwidget.CustomeTopAppBar
import com.example.uas.ui.navigation.DestinasiNavigasi
import com.example.uas.ui.viewmodel.Buku.HomeBukuUiState
import com.example.uas.ui.viewmodel.Buku.HomeBukuViewModel
import com.example.uas.ui.viewmodel.PenyediaViewModel

object DestinasiHomeBuku: DestinasiNavigasi {
    override val route = "home buku"
    override val titleRes = "Home Buku"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenBuku(
    navigateToItemEntry:()->Unit,
    modifier: Modifier = Modifier,
    onDetailClick:(String)->Unit = {},
    viewModel: HomeBukuViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomeTopAppBar(
                title = DestinasiHomeBuku.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getBook()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Buku")
            }
        }
    ) { innerpadding->
            HomeStatus(
                homeUiState = viewModel.bukuUIState,
                retryAction = {viewModel.getBook()}, modifier = Modifier.padding(innerpadding),
                onDetailClick = onDetailClick, onDeleteCLick = {
                    viewModel.deleteBook(it.id_buku)
                    viewModel.getBook()
                }
            )
    }
}

@Composable
fun HomeStatus(
    homeUiState: HomeBukuUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteCLick: (Buku) -> Unit = {},
    onDetailClick: (String) -> Unit
){
    when(homeUiState){
        is HomeBukuUiState.Loading-> OnLoading(modifier = modifier.fillMaxSize())
        is HomeBukuUiState.Success->
            if (homeUiState.buku.isEmpty()){
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Text(text = "Tidak ada data Buku")
                }
            }else{
                BukuLayout(
                    buku = homeUiState.buku, modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.id_buku)
                    },
                    onDeleteCLick = {
                        onDeleteCLick(it)
                    }
                )
            }
        is HomeBukuUiState.Error-> OnError(retryAction,modifier = modifier.fillMaxSize())
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
fun BukuLayout(
    buku: List<Buku>,
    modifier: Modifier = Modifier,
    onDetailClick: (Buku)->Unit,
    onDeleteCLick: (Buku) -> Unit = {}
){
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(buku){buku->
            BukuCard(
                buku = buku,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(buku) },
                onDeleteCLick = {
                    onDeleteCLick(buku)
                }
            )
        }
    }
}

@Composable
fun BukuCard(
    buku: Buku,
    modifier: Modifier = Modifier,
    onDeleteCLick:(Buku)-> Unit ={}
){
    var showDialog by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Konfirmasi") },
            text = { Text("Apakah Anda yakin ingin menghapus buku ${buku.judul}?") },
            confirmButton = {
                Button(
                    onClick = {
                        onDeleteCLick(buku)
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
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = buku.judul,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.weight(1f) // Mengisi ruang kosong di sebelah kiri
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = {showDialog = true}) { // Menampilkan dialog saat tombol delete ditekan
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null
                    )
                }
                Text(
                    text = buku.id_buku,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Penulis:",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(end = 4.dp)
                )
                Text(
                    text = buku.penulis,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Kategori:",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(end = 4.dp)
                )
                Text(
                    text = buku.kategori,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.Info, contentDescription = "")
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = buku.status,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}