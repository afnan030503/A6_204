package com.example.projectakhir.view.klien


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir.PenyediaViewModel
import com.example.projectakhir.cmwidget.CustomTopAppBar
import com.example.projectakhir.model.Klien
import com.example.projectakhir.navigasi.DestinasiNavigasi
import com.example.projectakhir.viewmodel.klien.DetailKlienUiState
import com.example.projectakhir.viewmodel.klien.DetailViewModelKlien
 
object DestinasiDetailKlien : DestinasiNavigasi {
    override val route = "detail_klien"
    override val titleRes = "Detail Klien"
    const val ID_KLIEN = "id_klien"
    val routesWithArg = "$route/{$ID_KLIEN}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailViewKlien(
    id_klien: Int,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModelKlien = viewModel(factory = PenyediaViewModel.Factory),
    onEditClick:() -> Unit = {},
    navigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = DestinasiDetailKlien.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = { viewModel.getDetailKlienByid() }
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
                    contentDescription = "Edit Klien"
                )
            }
        }
    ) { innerPadding ->
        DetaiStatuslKlien(
            modifier = Modifier.padding(innerPadding),
            detailUiState = viewModel.klienDetailKlienState,
            retryAction = { viewModel.getDetailKlienByid() }
        )
    }
}

@Composable
fun DetaiStatuslKlien(
    modifier: Modifier = Modifier,
    detailUiState: DetailKlienUiState,
    retryAction: () -> Unit = {}
) {
    when (detailUiState) {
        is DetailKlienUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is DetailKlienUiState.Success -> {
            if (detailUiState.klien.nama_klien.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Data tidak ditemukan.")
                }
            } else {
                ItemDetailKlien(
                    klien = detailUiState.klien,
                    modifier = modifier.fillMaxWidth()
                )
            }
        }

        is DetailKlienUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}


@Composable
fun ItemDetailKlien(
    modifier: Modifier = Modifier,
    klien: Klien
) {
    Card(
        modifier = modifier.padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailKlien(judul = "ID Klienn", isinya = klien.id_klien.toString())
            ComponentDetailKlien(judul = "Nama Klien", isinya = klien.nama_klien)
            ComponentDetailKlien(judul = "Kontak Klien", isinya = klien.kontak_klien)
        }
    }
}


@Composable
fun ComponentDetailKlien(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul :",
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
