package com.example.uas.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.uas.PerpustakaanApplications
import com.example.uas.ui.viewmodel.Anggota.DetailAnggotaViewModel
import com.example.uas.ui.viewmodel.Anggota.HomeAnggotaViewModel
import com.example.uas.ui.viewmodel.Anggota.InsertAnggotaViewModel
import com.example.uas.ui.viewmodel.Anggota.UpdateAnggotaViewModel
import com.example.uas.ui.viewmodel.Buku.DetailBukuViewModel
import com.example.uas.ui.viewmodel.Buku.HomeBukuViewModel
import com.example.uas.ui.viewmodel.Buku.InsertBukuViewModel
import com.example.uas.ui.viewmodel.Buku.UpdateBukuViewModel
import com.example.uas.ui.viewmodel.Peminjaman.DetailPeminjamanViewModel
import com.example.uas.ui.viewmodel.Peminjaman.HomePeminjamanViewModel
import com.example.uas.ui.viewmodel.Peminjaman.InsertPeminjamanViewModel
import com.example.uas.ui.viewmodel.Peminjaman.UpdatePeminjamanViewModel
import com.example.uas.ui.viewmodel.Pengembalian.DetailPengembalianViewModel
import com.example.uas.ui.viewmodel.Pengembalian.HomePengembalianViewModel
import com.example.uas.ui.viewmodel.Pengembalian.InsertPengembalianViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { HomeBukuViewModel(aplikasiPerpustakaan().container.bukuRepository) }
        initializer { InsertBukuViewModel(aplikasiPerpustakaan().container.bukuRepository) }
        initializer { DetailBukuViewModel(createSavedStateHandle(), aplikasiPerpustakaan().container.bukuRepository) }
        initializer { UpdateBukuViewModel(createSavedStateHandle(), aplikasiPerpustakaan().container.bukuRepository) }

        initializer { HomeAnggotaViewModel(aplikasiPerpustakaan().container.anggotaRepository) }
        initializer { InsertAnggotaViewModel(aplikasiPerpustakaan().container.anggotaRepository) }
        initializer { DetailAnggotaViewModel(createSavedStateHandle(), aplikasiPerpustakaan().container.anggotaRepository) }
        initializer { UpdateAnggotaViewModel(createSavedStateHandle(), aplikasiPerpustakaan().container.anggotaRepository) }

        initializer { HomePeminjamanViewModel(aplikasiPerpustakaan().container.peminjamanRepository) }
        initializer { InsertPeminjamanViewModel(aplikasiPerpustakaan().container.peminjamanRepository) }
        initializer { DetailPeminjamanViewModel(createSavedStateHandle(), aplikasiPerpustakaan().container.peminjamanRepository) }
        initializer { UpdatePeminjamanViewModel(createSavedStateHandle(), aplikasiPerpustakaan().container.peminjamanRepository) }

        initializer { HomePengembalianViewModel(aplikasiPerpustakaan().container.pengembalianRepository) }
        initializer { InsertPengembalianViewModel(aplikasiPerpustakaan().container.pengembalianRepository) }
        initializer { DetailPengembalianViewModel(createSavedStateHandle(), aplikasiPerpustakaan().container.pengembalianRepository) }
    }
}


fun CreationExtras.aplikasiPerpustakaan(): PerpustakaanApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PerpustakaanApplications)