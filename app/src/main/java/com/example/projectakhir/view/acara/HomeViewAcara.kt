package com.example.projectakhir.view.acara

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir.PenyediaViewModel
import com.example.projectakhir.cmwidget.CustomTopAppBar
import com.example.projectakhir.model.Acara
import com.example.projectakhir.model.Klien
import com.example.projectakhir.model.Lokasi
import com.example.projectakhir.navigasi.DestinasiNavigasi
import com.example.projectakhir.view.klien.OnError
import com.example.projectakhir.view.klien.OnLoading
import com.example.projectakhir.viewmodel.acara.HomeAcaraUiState
import com.example.projectakhir.viewmodel.acara.HomeViewModelAcara

object DestinasiHomeAcara : DestinasiNavigasi {
    override val route = "acara"
    override val titleRes = "Halaman Acara"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenAcara(
    navigateToAddAcara: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit = {},
    viewModel: HomeViewModelAcara = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiHomeAcara.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getAcara()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToAddAcara,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Tambah Acara")
            }
        },
    ) { innerPadding ->
        AcaraStatus(
            acaraUiState = viewModel.acaraUiState,
            klienList = viewModel.klienMap,
            lokasiList = viewModel.lokasiMap,
            retryAction = { viewModel.getAcara() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deleteAcara(it.id_acara)
                viewModel.getAcara() // Refresh data setelah penghapusan
            }
        )
    }
}

@Composable
fun AcaraStatus(
    acaraUiState: HomeAcaraUiState,
    klienList: MutableState<List<Klien>>,
    lokasiList: MutableState<List<Lokasi>>,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Acara) -> Unit,
    onDetailClick: (Int) -> Unit
) {
    when (acaraUiState) {
        is HomeAcaraUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is HomeAcaraUiState.Success ->
            if (acaraUiState.acara.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada acara")
                }
            } else {
                AcaraLayout(
                    acara = acaraUiState.acara,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.id_acara)
                    },
                    onDeleteClick = {
                        onDeleteClick(it)
                    }
                )
            }
        is HomeAcaraUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun AcaraLayout(
    acara: List<Acara>,
    modifier: Modifier = Modifier,
    onDetailClick: (Acara) -> Unit,
    onDeleteClick: (Acara) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(acara) { acara ->
            AcaraCard(
                id_acara = acara.id_acara,
                nama_acara = acara.nama_acara,
                deskripsi_acara = acara.deskripsi_acara,
                id_klien = acara.id_klien,
                id_lokasi = acara.id_lokasi,
                status_acara = acara.status_acara,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(acara) },
                onDeleteClick = { onDeleteClick(acara) }
            )
        }
    }
}

@Composable
fun AcaraCard(
    id_acara: Int,
    nama_acara: String,
    deskripsi_acara: String,
    id_klien: Int,
    id_lokasi: Int,
    status_acara:String,
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .shadow(4.dp, shape = MaterialTheme.shapes.medium),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(Color.Transparent),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            )

            {
                Text(
                    text = "ID ACARA: $id_acara",
                    style = MaterialTheme.typography.titleMedium,
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick() }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                    )
                }
            }
            Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f), thickness = 1.dp)
            Spacer(modifier = Modifier.height(1.dp))

            Text(
                text = " $nama_acara",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.align(Alignment.CenterHorizontally)

            )

            Spacer(modifier = Modifier.height(1.dp))
            Text(
                text = "Deskripsi: $deskripsi_acara",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )

            Text(
                text = "ID Klien: $id_klien",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )

            Text(
                text = "ID Lokasi: $id_lokasi",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )

            Text(
                text = "Status: $status_acara",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}