package com.example.projectakhir.dependenciesinjection

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.projectakhir.view.acara.DestinasiAcaraEntry
import com.example.projectakhir.view.acara.DestinasiDetailAcara
import com.example.projectakhir.view.acara.DestinasiHomeAcara
import com.example.projectakhir.view.acara.DestinasiUpdateAcara
import com.example.projectakhir.view.acara.DetailViewAcara
import com.example.projectakhir.view.acara.EntryAcaraScreen
import com.example.projectakhir.view.acara.HomeScreenAcara
import com.example.projectakhir.view.acara.UpdateViewAcara
import com.example.projectakhir.view.klien.*
import com.example.projectakhir.view.lokasi.DestinasiDetailLokasi
import com.example.projectakhir.view.lokasi.DestinasiHomeLokasi
import com.example.projectakhir.view.lokasi.DestinasiLokasiEntry
import com.example.projectakhir.view.lokasi.DestinasiUpdateLokasi
import com.example.projectakhir.view.lokasi.DetailViewLokasi
import com.example.projectakhir.view.lokasi.EntryLokasiScreen
import com.example.projectakhir.view.lokasi.HomeScreenLokasi
import com.example.projectakhir.view.lokasi.UpdateViewLokasi
import com.example.projectakhir.view.vendor.DestinasiDetailVendor
import com.example.projectakhir.view.vendor.DestinasiHomeVendor
import com.example.projectakhir.view.vendor.DestinasiUpdateVendor
import com.example.projectakhir.view.vendor.DestinasiVendorEntry
import com.example.projectakhir.view.vendor.DetailViewVendor
import com.example.projectakhir.view.vendor.EntryVendorScreen
import com.example.projectakhir.view.vendor.HomeScreenVendor
import com.example.projectakhir.view.vendor.UpdateViewVendor
import com.example.projectakhir.viewmodel.klien.HomeKlienUiState
import com.example.projectakhir.viewmodel.lokasi.HomeLokasiUiState

@Composable
fun PengelolaHalaman(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHomeAcara.route,
        modifier = modifier,
    ) {

        composable(DestinasiHomeAcara.route) {
            HomeScreenAcara(
                navigateToAddAcara = { navController.navigate(DestinasiAcaraEntry.route) },
                navigateToAcara = {navController.navigate(DestinasiHomeAcara.route)},
                navigateToLokasi = {navController.navigate(DestinasiHomeLokasi.route)},
                navigateToKlien = {navController.navigate(DestinasiHomeKlien.route)},
                navigateToVendor = {navController.navigate(DestinasiHomeVendor.route)},
                onDetailClick = { id_acara ->
                    navController.navigate("${DestinasiDetailAcara.route}/$id_acara") {
                        popUpTo(DestinasiHomeAcara.route) { inclusive = true }
                    }
                }
            )
        }
// Halaman Entry untuk menambah Acara baru
        composable(DestinasiAcaraEntry.route) {
            EntryAcaraScreen(
                navigateBack = {
                    navController.navigate(DestinasiHomeAcara.route) {
                        popUpTo(DestinasiHomeAcara.route) { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }

        // Halaman Detail untuk melihat informasi Acara
        composable(
            route = DestinasiDetailAcara.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailAcara.ID_ACARA) { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val id_acara = backStackEntry.arguments?.getInt(DestinasiDetailAcara.ID_ACARA)
            id_acara?.let {
                DetailViewAcara(
                    id_acara = it,
                    navigateBack = {
                        navController.navigate(DestinasiHomeAcara.route) {
                            popUpTo(DestinasiHomeAcara.route) { inclusive = true }
                        }
                    },
                    onEditClick = {
                        navController.navigate("${DestinasiUpdateAcara.route}/$id_acara")
                    }
                )
            }
        }

        // Halaman Update untuk mengedit informasi Acara
        composable(
            route = DestinasiUpdateAcara.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateAcara.ID_ACARA) { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val id_acara = backStackEntry.arguments?.getInt(DestinasiUpdateAcara.ID_ACARA)
            id_acara?.let {
                UpdateViewAcara(
                    id_acara = it,
                    navigateBack = { navController.popBackStack() },
                    modifier = modifier
                )
            }
        }
        // Halaman Home untuk menampilkan daftar klien
        composable(DestinasiHomeKlien.route) {
            HomeScreenKlien(
                navigateToItemEntry = { navController.navigate(DestinasiKlienEntry.route) },
                navigateToAcara = {navController.navigate(DestinasiHomeAcara.route)},
                navigateToLokasi = {navController.navigate(DestinasiHomeLokasi.route)},
                navigateToKlien = {navController.navigate(DestinasiHomeKlien.route)},
                navigateToVendor = {navController.navigate(DestinasiHomeVendor.route)},
                onDetailClick = { id_klien -> navController.navigate("${DestinasiDetailKlien.route}/$id_klien") {popUpTo(DestinasiHomeKlien.route) { inclusive = true } } }
            )
        }

        // Halaman Entry untuk menambah klien baru
        composable(DestinasiKlienEntry.route) {
            EntryKlienScreen(
                navigateBack = {
                    navController.navigate(DestinasiHomeKlien.route) {
                        popUpTo(DestinasiHomeKlien.route) { inclusive = true }
                    }
                }
            )
        }

        // Halaman Detail untuk melihat informasi klien
        composable(
            route = DestinasiDetailKlien.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailKlien.ID_KLIEN) { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val id_klien = backStackEntry.arguments?.getInt(DestinasiDetailKlien.ID_KLIEN)
            id_klien?.let {
                DetailViewKlien(
                    id_klien = it,
                    navigateBack = {
                        navController.navigate(DestinasiHomeKlien.route) {
                            popUpTo(DestinasiHomeKlien.route) { inclusive = true }
                        }
                    },
                    onEditClick = { navController.navigate("${DestinasiUpdateKlien.route}/$id_klien") }
                )
            }
        }

        // Halaman Update untuk mengedit informasi klien
        composable(
            route = DestinasiUpdateKlien.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateKlien.ID_KLIEN) { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val id_klien = backStackEntry.arguments?.getInt(DestinasiUpdateKlien.ID_KLIEN)
            id_klien?.let {
                UpdateViewKlien(
                    id_klien = it,
                    navigateBack = { navController.popBackStack() },
                    modifier = modifier
                )
            }

        }
        // Halaman Home untuk menampilkan daftar lokasi
        composable(DestinasiHomeLokasi.route) {
            HomeScreenLokasi(
                navigateToAddLokasi = { navController.navigate(DestinasiLokasiEntry.route) },
                navigateToAcara = {navController.navigate(DestinasiHomeAcara.route)},
                navigateToLokasi = {navController.navigate(DestinasiHomeLokasi.route)},
                navigateToKlien = {navController.navigate(DestinasiHomeKlien.route)},
                navigateToVendor = {navController.navigate(DestinasiHomeVendor.route)},
                onDetailClick = { id_lokasi ->
                    navController.navigate("${DestinasiDetailLokasi.route}/$id_lokasi") {
                        popUpTo(DestinasiHomeLokasi.route) { inclusive = true }
                    }
                }
            )
        }

        // Halaman Entry untuk menambah lokasi baru
        composable(DestinasiLokasiEntry.route) {
            EntryLokasiScreen(
                navigateBack = {
                    navController.navigate(DestinasiHomeLokasi.route) {
                        popUpTo(DestinasiHomeLokasi.route) { inclusive = true }
                    }
                }
            )
        }

        // Halaman Detail untuk melihat informasi lokasi
        composable(
            route = DestinasiDetailLokasi.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailLokasi.ID_LOKASI) { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val id_lokasi = backStackEntry.arguments?.getInt(DestinasiDetailLokasi.ID_LOKASI)
            id_lokasi?.let {
                DetailViewLokasi(
                    id_lokasi = it,
                    navigateBack = {
                        navController.navigate(DestinasiHomeLokasi.route) {
                            popUpTo(DestinasiHomeLokasi.route) { inclusive = true }
                        }
                    },
                    onEditClick = { navController.navigate("${DestinasiUpdateLokasi.route}/$id_lokasi") }
                )
            }
        }

        // Halaman Update untuk mengedit informasi lokasi
        composable(
            route = DestinasiUpdateLokasi.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateLokasi.ID_LOKASI) { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val id_lokasi = backStackEntry.arguments?.getInt(DestinasiUpdateLokasi.ID_LOKASI)
            id_lokasi?.let {
                UpdateViewLokasi(
                    id_lokasi = it,
                    navigateBack = { navController.popBackStack() },
                    modifier = modifier
                )
            }

        }
        // Halaman Home untuk menampilkan daftar Vendor
        composable(DestinasiHomeVendor.route) {
            HomeScreenVendor(
                navigateToAddVendor = { navController.navigate(DestinasiVendorEntry.route) },
                onDetailClick = { id_vendor -> navController.navigate("${DestinasiDetailVendor.route}/$id_vendor") {
                        popUpTo(DestinasiHomeVendor.route) { inclusive = true } } },
                navigateToAcara = {navController.navigate(DestinasiHomeAcara.route)},
                navigateToLokasi = {navController.navigate(DestinasiHomeLokasi.route)},
                navigateToKlien = {navController.navigate(DestinasiHomeKlien.route)},
                navigateToVendor = {navController.navigate(DestinasiHomeVendor.route)},
            )
        }

        // Halaman Entry untuk menambah vendor baru
        composable(DestinasiVendorEntry.route) {
            EntryVendorScreen(
                navigateBack = {
                    navController.navigate(DestinasiHomeVendor.route) {
                        popUpTo(DestinasiHomeVendor.route) { inclusive = true }
                    }
                }
            )
        }

        // Halaman Detail untuk melihat informasi vendor
        composable(
            route = DestinasiDetailVendor.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailVendor.ID_VENDOR) { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val id_vendor = backStackEntry.arguments?.getInt(DestinasiDetailVendor.ID_VENDOR)
            id_vendor?.let {
                DetailViewVendor(
                    id_vendor = it,
                    navigateBack = {
                        navController.navigate(DestinasiHomeVendor.route) {
                            popUpTo(DestinasiHomeVendor.route) { inclusive = true }
                        }
                    },
                    onEditClick = { navController.navigate("${DestinasiUpdateVendor.route}/$id_vendor") }
                )
            }
        }

        // Halaman Update untuk mengedit informasi vendor
        composable(
            route = DestinasiUpdateVendor.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateVendor.ID_VENDOR) { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val id_vendor = backStackEntry.arguments?.getInt(DestinasiUpdateVendor.ID_VENDOR)
            id_vendor?.let {
                UpdateViewVendor(
                    id_vendor = it,
                    navigateBack = { navController.popBackStack() },
                    modifier = modifier
                )
            }
        }
    }
}

