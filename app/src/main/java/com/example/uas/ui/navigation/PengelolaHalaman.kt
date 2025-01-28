package com.example.uas.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.uas.ui.view.Anggota.DestinasiDetailAnggota
import com.example.uas.ui.view.Anggota.DestinasiEditAnggota
import com.example.uas.ui.view.Anggota.DestinasiEntryAnggota
import com.example.uas.ui.view.Anggota.DestinasiHomeAnggota
import com.example.uas.ui.view.Anggota.DetailAnggotaView
import com.example.uas.ui.view.Anggota.EntryAnggotaScreen
import com.example.uas.ui.view.Anggota.HomeScreenAnggota
import com.example.uas.ui.view.Anggota.UpdateAnggotaView
import com.example.uas.ui.view.Buku.DestinasiDetailBuku
import com.example.uas.ui.view.Buku.DestinasiEditBuku
import com.example.uas.ui.view.Buku.DestinasiEntryBuku
import com.example.uas.ui.view.Buku.DestinasiHomeBuku
import com.example.uas.ui.view.Buku.DetailBukuView
import com.example.uas.ui.view.Buku.EntryBukuScreen
import com.example.uas.ui.view.Buku.HomeScreenBuku
import com.example.uas.ui.view.Buku.UpdateBukuView
import com.example.uas.ui.view.DestinasiSplash
import com.example.uas.ui.view.Peminjaman.DestinasiDetailPeminjaman
import com.example.uas.ui.view.Peminjaman.DestinasiEditPeminjaman
import com.example.uas.ui.view.Peminjaman.DestinasiEntryPeminjaman
import com.example.uas.ui.view.Peminjaman.DestinasiHomePeminjaman
import com.example.uas.ui.view.Peminjaman.DetailPeminjamanView
import com.example.uas.ui.view.Peminjaman.EntryPeminjamanScreen
import com.example.uas.ui.view.Peminjaman.HomeScreenPeminjaman
import com.example.uas.ui.view.Peminjaman.UpdatePeminjamanView
import com.example.uas.ui.view.Pengembalian.DestinasiDetailPengembalian
import com.example.uas.ui.view.Pengembalian.DestinasiEditPengembalian
import com.example.uas.ui.view.Pengembalian.DestinasiEntryPengembalian
import com.example.uas.ui.view.Pengembalian.DestinasiHomePengembalian
import com.example.uas.ui.view.Pengembalian.DetailPengembalianView
import com.example.uas.ui.view.Pengembalian.EntryPengembalianScreen
import com.example.uas.ui.view.Pengembalian.HomeScreenPengembalian
import com.example.uas.ui.view.Pengembalian.UpdatePengembalianView
import com.example.uas.ui.view.SplashView

@Composable
fun PengelolaHalaman(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiSplash.route,
        modifier = modifier.padding()
    ) {
        // Halaman Splash
        composable(route = DestinasiSplash.route) {
            SplashView(
                onBukuButtonClicked = { navController.navigate(DestinasiHomeBuku.route) },
                onAnggotaButtonClicked = { navController.navigate(DestinasiHomeAnggota.route) },
                onPeminjamanButtonClicked = { navController.navigate(DestinasiHomePeminjaman.route) },
                onPengembalianButtonClicked = { navController.navigate(DestinasiHomePengembalian.route) }
            )
        }

        // Halaman Manajemen Buku
        composable(
            route = DestinasiHomeBuku.route
        ){
            HomeScreenBuku(
                navigateToItemEntry = { navController.navigate(DestinasiEntryBuku.route) },
                onDetailClick = { id_buku ->
                    navController.navigate("${DestinasiDetailBuku.route}/$id_buku")
                    println(id_buku)
                }
            )
        }
        composable(
            route = DestinasiEntryBuku.route
        ){
            EntryBukuScreen(
                navigateBack = {
                    navController.navigate(DestinasiHomeBuku.route) {
                        popUpTo(DestinasiHomeBuku.route) { inclusive = true }
                    }
                }
            )
        }
        composable(
            route = DestinasiDetailBuku.routeWithArgs,
            arguments = listOf(navArgument(DestinasiDetailBuku.id_buku){
                type = NavType.StringType
            })
        ){ backStackEntry ->
            val id_buku = backStackEntry.arguments?.getString(DestinasiDetailBuku.id_buku)
            id_buku?.let {
                DetailBukuView(
                    navigateBack = {
                        navController.navigateUp()
                    },
                    onEditClick = { id_buku ->
                        navController.navigate("${DestinasiEditBuku.route}/$id_buku")
                        println(id_buku)
                    }
                )
            }
        }
        composable(
            route = DestinasiEditBuku.routeWithArgs,
            arguments = listOf(navArgument(DestinasiEditBuku.id_buku){
                type = NavType.StringType
            })
        ){
            UpdateBukuView(
                navigateBack = {
                    navController.popBackStack()
                },
                onNavigateUp = {
                    navController.navigate(
                        DestinasiEditBuku.route
                    ){
                        popUpTo(DestinasiHomeBuku.route){
                            inclusive = true
                        }
                    }
                }
            )
        }


        // Halaman Manajemen Anggota
        composable(
            route = DestinasiHomeAnggota.route
        ){
            HomeScreenAnggota(
                navigateToItemEntry = { navController.navigate(DestinasiEntryAnggota.route) },
                onDetailClick = { id_anggota ->
                    navController.navigate("${DestinasiDetailAnggota.route}/$id_anggota")
                    println(id_anggota)
                }
            )
        }
        composable(
            route = DestinasiEntryAnggota.route
        ){
            EntryAnggotaScreen(
                navigateBack = {
                    navController.navigate(DestinasiHomeAnggota.route) {
                        popUpTo(DestinasiHomeAnggota.route) { inclusive = true }
                    }
                }
            )
        }
        composable(
            route = DestinasiDetailAnggota.routeWithArgs,
            arguments = listOf(navArgument(DestinasiDetailAnggota.id_anggota){
                type = NavType.StringType
            })
        ){ backStackEntry ->
            val id_anggota = backStackEntry.arguments?.getString(DestinasiDetailAnggota.id_anggota)
            id_anggota?.let {
                DetailAnggotaView(
                    navigateBack = {
                        navController.navigateUp()
                    },
                    onEditClick = { id_anggota ->
                        navController.navigate("${DestinasiEditAnggota.route}/$id_anggota")
                        println(id_anggota)
                    }
                )
            }
        }
        composable(
            route = DestinasiEditAnggota.routeWithArgs,
            arguments = listOf(navArgument(DestinasiEditAnggota.id_anggota){
                type = NavType.StringType
            })
        ){
            UpdateAnggotaView(
                navigateBack = {
                    navController.popBackStack()
                },
                onNavigateUp = {
                    navController.navigate(
                        DestinasiEditBuku.route
                    ){
                        popUpTo(DestinasiHomeBuku.route){
                            inclusive = true
                        }
                    }
                }
            )
        }

        // Halaman Manajemen Peminjaman
        composable(
            route = DestinasiHomePeminjaman.route
        ){
            HomeScreenPeminjaman(
                navigateToItemEntry = { navController.navigate(DestinasiEntryPeminjaman.route) },
                onDetailClick = { id_peminjaman ->
                    navController.navigate("${DestinasiDetailPeminjaman.route}/$id_peminjaman")
                    println(id_peminjaman)
                }
            )
        }
        composable(
            route = DestinasiEntryPeminjaman.route
        ){
            EntryPeminjamanScreen(
                navigateBack = {
                    navController.navigate(DestinasiHomePeminjaman.route) {
                        popUpTo(DestinasiHomePeminjaman.route) { inclusive = true }
                    }
                }
            )
        }
        composable(
            route = DestinasiDetailPeminjaman.routeWithArgs,
            arguments = listOf(navArgument(DestinasiDetailPeminjaman.id_peminjaman){
                type = NavType.StringType
            })
        ){ backStackEntry ->
            val id_peminjaman = backStackEntry.arguments?.getString(DestinasiDetailPeminjaman.id_peminjaman)
            id_peminjaman?.let {
                DetailPeminjamanView(
                    navigateBack = {
                        navController.navigateUp()
                    },
                    onEditClick = { id_peminjaman ->
                        navController.navigate("${DestinasiEditPeminjaman.route}/$id_peminjaman")
                        println(id_peminjaman)
                    }
                )
            }
        }
        composable(
            route = DestinasiEditPeminjaman.routeWithArgs,
            arguments = listOf(navArgument(DestinasiEditPeminjaman.id_peminjaman){
                type = NavType.StringType
            })
        ){
            UpdatePeminjamanView(
                navigateBack = {
                    navController.popBackStack()
                },
                onNavigateUp = {
                    navController.navigate(
                        DestinasiEditPeminjaman.route
                    ){
                        popUpTo(DestinasiHomePeminjaman.route){
                            inclusive = true
                        }
                    }
                }
            )
        }

        // Halaman Manajemen Pengembalian
        composable(
            route = DestinasiHomePengembalian.route
        ){
            HomeScreenPengembalian(
                navigateToItemEntry = { navController.navigate(DestinasiEntryPengembalian.route) },
                onDetailClick = { id_pengembalian ->
                    navController.navigate("${DestinasiDetailPengembalian.route}/$id_pengembalian")
                    println(id_pengembalian)
                }
            )
        }
        composable(
            route = DestinasiEntryPengembalian.route
        ){
            EntryPengembalianScreen(
                navigateBack = {
                    navController.navigate(DestinasiHomePengembalian.route) {
                        popUpTo(DestinasiHomePengembalian.route) { inclusive = true }
                    }
                }
            )
        }
        composable(
            route = DestinasiDetailPengembalian.routeWithArgs,
            arguments = listOf(navArgument(DestinasiDetailPengembalian.id_pengembalian){
                type = NavType.StringType
            })
        ){ backStackEntry ->
            val id_pengembalian = backStackEntry.arguments?.getString(DestinasiDetailPengembalian.id_pengembalian)
            id_pengembalian?.let {
                DetailPengembalianView(
                    navigateBack = {
                        navController.navigateUp()
                    },
                    onEditClick = { id_peminjaman ->
                        navController.navigate("${DestinasiEditPeminjaman.route}/$id_peminjaman")
                        println(id_peminjaman)
                    }
                )
            }
        }
        composable(
            route = DestinasiEditPengembalian.routeWithArgs,
            arguments = listOf(navArgument(DestinasiEditPengembalian.id_pengembalian){
                type = NavType.StringType
            })
        ){
            UpdatePengembalianView(
                navigateBack = {
                    navController.popBackStack()
                },
                onNavigateUp = {
                    navController.navigate(
                        DestinasiEditPengembalian.route
                    ){
                        popUpTo(DestinasiHomePengembalian.route){
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}
