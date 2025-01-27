package com.example.projectakhir.view.acara

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir.PenyediaViewModel
import com.example.projectakhir.cmwidget.CustomTopAppBar
import com.example.projectakhir.model.Acara
import com.example.projectakhir.model.Klien
import com.example.projectakhir.model.Lokasi
import com.example.projectakhir.navigasi.DestinasiNavigasi
import com.example.projectakhir.viewmodel.acara.DetailAcaraUiState
import com.example.projectakhir.viewmodel.acara.DetailViewModelAcara
import java.text.SimpleDateFormat
import java.util.*


object DestinasiDetailAcara : DestinasiNavigasi {
    override val route = "detail_acara"
    override val titleRes = "Detail Acara"
    const val ID_ACARA = "id_acara"
    val routesWithArg = "$route/{$ID_ACARA}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailViewAcara(
    id_acara: Int,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModelAcara = viewModel(factory = PenyediaViewModel.Factory),
    onEditClick: () -> Unit = {},
    navigateBack: () -> Unit,
) {
    val detailUiState = viewModel.acaraDetailState // Memantau state acara

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = DestinasiDetailAcara.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = { viewModel.getDetailAcara() }
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
                    contentDescription = "Edit Acara"
                )
            }
        }
    ) { innerPadding ->
        BodyDetailAcara(
            modifier = Modifier.padding(innerPadding),
            detailUiState = detailUiState,
            retryAction = { viewModel.getDetailAcara() }
        )
    }
}

@Composable
fun BodyDetailAcara(
    modifier: Modifier = Modifier,
    detailUiState: DetailAcaraUiState,
    retryAction: () -> Unit = {}
) {
    when (detailUiState) {
        is DetailAcaraUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is DetailAcaraUiState.Success -> {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(17.dp)
            ) {
                ItemDetailAcara(
                    acara = detailUiState.acara,
                    klien = detailUiState.klien,
                    lokasi = detailUiState.lokasi
                )
            }
        }
        is DetailAcaraUiState.Error -> OnError(
            retryAction = retryAction,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
fun ItemDetailAcara(
    acara: Acara,
    klien: Klien?,
    lokasi: Lokasi?
) {
    // Membuat SimpleDateFormat untuk tanggal
    val dateFormatter = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))

    // Mengubah tanggal mulai dan berakhir menjadi format yang diinginkan
    val formattedTanggalMulai = try {
        val date = SimpleDateFormat("yyyy-MM-dd", Locale("id", "ID")).parse(acara.tanggal_mulai)
        dateFormatter.format(date) // Mengubah ke format yang diinginkan
    } catch (e: Exception) {
        acara.tanggal_mulai // Menggunakan tanggal asli jika terjadi error
    }

    val formattedTanggalBerakhir = try {
        val date = SimpleDateFormat("yyyy-MM-dd", Locale("id", "ID")).parse(acara.tanggal_berakhir)
        dateFormatter.format(date) // Mengubah ke format yang diinginkan
    } catch (e: Exception) {
        acara.tanggal_berakhir // Menggunakan tanggal asli jika terjadi error
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            ComponentDetailAcara(judul = "Nama Acara", isinya = acara.nama_acara)
            Spacer(modifier = Modifier.padding(5.dp))
            ComponentDetailAcara(judul = "Deskripsi Acara", isinya = acara.deskripsi_acara)
            Spacer(modifier = Modifier.padding(5.dp))
            ComponentDetailAcara(judul = "Tanggal Mulai", isinya = formattedTanggalMulai)
            Spacer(modifier = Modifier.padding(5.dp))
            ComponentDetailAcara(judul = "Tanggal Berakhir", isinya = formattedTanggalBerakhir)
            Spacer(modifier = Modifier.padding(5.dp))
            ComponentDetailAcara(judul = "Status Acara", isinya = acara.status_acara)
            Spacer(modifier = Modifier.padding(5.dp))
            ComponentDetailAcara(
                judul = "Klien",
                isinya = (klien?.id_klien ?: "Data klien tidak tersedia").toString()
            )
            Spacer(modifier = Modifier.padding(5.dp))
            ComponentDetailAcara(
                judul = "Lokasi",
                isinya = (lokasi?.id_lokasi ?: "Data lokasi tidak tersedia").toString()
            )
        }
    }
}


@Composable
fun ComponentDetailAcara(
    judul: String,
    isinya: String
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "$judul:",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center // Jika ingin teks judul di tengah
        )
        Text(
            text = isinya,
            fontSize = 22.sp,
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.Center // Jika ingin teks
        )
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun OnError(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Terjadi kesalahan, coba lagi.")
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = retryAction) {
                Text(text = "Coba Lagi")
            }
        }
    }
}