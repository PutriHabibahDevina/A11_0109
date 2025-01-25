package com.example.uas.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.uas.R
import com.example.uas.ui.navigation.DestinasiNavigasi

object DestinasiSplash : DestinasiNavigasi {
    override val route = "destinasi_splash"
    override val titleRes = "Splash"
}

@Composable
fun SplashView(
    onBukuButtonClicked: () -> Unit,
    onAnggotaButtonClicked: () -> Unit,
    onPeminjamanButtonClicked: () -> Unit,
    onPengembalianButtonClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.purple_200)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_perpus),
            contentDescription = "Logo Perpustakaan"
        )
        Spacer(modifier = Modifier.padding(16.dp))
        Button(
            onClick = { onBukuButtonClicked() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            Text(text = "Manajemen Buku")
        }

        Spacer(modifier = Modifier.padding(16.dp))
        Button(
            onClick = { onAnggotaButtonClicked() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            Text(text = "Manajemen Anggota")
        }

        Spacer(modifier = Modifier.padding(16.dp))
        Button(
            onClick = { onPeminjamanButtonClicked() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            Text(text = "Manajemen Peminjaman")
        }

        Spacer(modifier = Modifier.padding(16.dp))
        Button(
            onClick = { onPengembalianButtonClicked() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            Text(text = "Manajemen Pengembalian")
        }
    }
}