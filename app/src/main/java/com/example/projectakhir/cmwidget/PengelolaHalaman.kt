package com.example.projectakhir.cmwidget

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.projectakhir.view.klien.*

@Composable
fun PengelolaHalaman(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHomeKlien.route,
        modifier = modifier,
    ) {
        // Halaman Home untuk menampilkan daftar klien
        composable(DestinasiHomeKlien.route) {
            HomeScreenKlien(
                navigateToItemEntry = { navController.navigate(DestinasiKlienEntry.route) },
                onDetailClick = { id_klien ->
                    navController.navigate("${DestinasiDetailKlien.route}/$id_klien") {
                        popUpTo(DestinasiHomeKlien.route) { inclusive = true }
                    }
                }
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
    }
}
