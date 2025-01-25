package com.example.projectakhir.view.lokasi


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir.PenyediaViewModel
import com.example.projectakhir.R
import com.example.projectakhir.cmwidget.CustomTopAppBar
import com.example.projectakhir.model.Lokasi
import com.example.projectakhir.navigasi.DestinasiNavigasi
import com.example.projectakhir.view.klien.ComponentDetailKlien
import com.example.projectakhir.view.klien.OnError
import com.example.projectakhir.view.klien.OnLoading
import com.example.projectakhir.viewmodel.lokasi.DetailLokasiUiState
import com.example.projectakhir.viewmodel.lokasi.DetailViewModelLokasi


object DestinasiDetailLokasi : DestinasiNavigasi {
    override val route = "detail_lokasi"
    override val titleRes = "Detail Lokasi"
    const val ID_LOKASI = "id_lokasi"
    val routesWithArg = "$route/{$ID_LOKASI}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailViewLokasi(
    id_lokasi: Int,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModelLokasi = viewModel(factory = PenyediaViewModel.Factory),
    onEditClick: () -> Unit = {},
    navigateBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = DestinasiDetailLokasi.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = { viewModel.getDetailLokasiById() } // Trigger refresh action
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onEditClick,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(17.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Lokasi"
                )
            }
        }
    ) { innerPadding ->
        BodyDetailLokasi(
            modifier = Modifier.padding(innerPadding),
            detailUiState = viewModel.lokasiDetailState,
            retryAction = { viewModel.getDetailLokasiById() }
        )
    }
}

@Composable
fun BodyDetailLokasi(
    modifier: Modifier = Modifier,
    detailUiState: DetailLokasiUiState,
    retryAction: () -> Unit = {}
) {
    when (detailUiState) {
        is DetailLokasiUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is DetailLokasiUiState.Success -> {
            if (detailUiState.lokasi.nama_lokasi.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Data tidak ditemukan.")
                }
            } else {
                ItemDetailLokasi(
                    lokasi = detailUiState.lokasi,
                    modifier = modifier.fillMaxWidth()
                )
            }
        }

        is DetailLokasiUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}


@Composable
fun ItemDetailLokasi(
    modifier: Modifier = Modifier,
    lokasi: Lokasi
) {
    Card(
        modifier = Modifier.padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailLokasi(judul = "ID Lokasi", isinya = lokasi.id_lokasi.toString())
            ComponentDetailLokasi(judul = "Nama Lokasi", isinya = lokasi.nama_lokasi)
            ComponentDetailLokasi(judul = "Alamat Lokasi", isinya = lokasi.alamat_lokasi)
            ComponentDetailLokasi(judul = "Kapasitas", isinya = lokasi.kapasitas.toString())
        }
    }
}

@Composable
fun ComponentDetailLokasi(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul:",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = isinya,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
    }
}