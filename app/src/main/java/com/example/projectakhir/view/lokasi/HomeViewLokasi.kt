package com.example.projectakhir.view.lokasi

import androidx.compose.foundation.Image
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir.PenyediaViewModel
import com.example.projectakhir.cmwidget.CustomTopAppBar
import com.example.projectakhir.model.Lokasi
import com.example.projectakhir.navigasi.DestinasiNavigasi
import com.example.projectakhir.view.klien.OnError
import com.example.projectakhir.view.klien.OnLoading
import com.example.projectakhir.viewmodel.lokasi.HomeLokasiUiState
import com.example.projectakhir.viewmodel.lokasi.HomeViewModelLokasi


object DestinasiHomeLokasi : DestinasiNavigasi {
    override val route = "home_lokasi"
    override val titleRes = "Halaman Lokasi"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenLokasi(
    navigateToAddLokasi: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: HomeViewModelLokasi = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiHomeLokasi.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onRefresh = { viewModel.getLokasi() }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToAddLokasi,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Tambah Lokasi")
            }
        },
    ) { innerPadding ->
        HomeStatusLokasi(
            lokasiUiState = viewModel.lokasiUiState,
            retryAction = { viewModel.getLokasi() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick, onDeleteClick = {
                viewModel.deleteLokasi(it.id_lokasi) }
        )
    }
}

@Composable
fun HomeStatusLokasi(
    lokasiUiState: HomeLokasiUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Lokasi) -> Unit,
    onDetailClick: (String) -> Unit
) {
    when (lokasiUiState) {
        is HomeLokasiUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomeLokasiUiState.Success ->
            if (lokasiUiState.lokasi.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada lokasi")
                }
            } else {
                LokasiLayout(
                    lokasi = lokasiUiState.lokasi,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.id_lokasi.toString())
                    },
                    onDeleteClick = {onDeleteClick(it)
                    }
                )
            }
            is HomeLokasiUiState.Error -> OnError(retryAction = retryAction, modifier = modifier.fillMaxSize())
        }

    }


@Composable
fun LokasiLayout(
    lokasi: List<Lokasi>,
    modifier: Modifier = Modifier,
    onDetailClick: (Lokasi) -> Unit,
    onDeleteClick: (Lokasi) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(lokasi) { lokasi ->
            LokasiCard(
                lokasi = lokasi,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(lokasi) },
                onDeleteClick = { onDeleteClick(lokasi) }
            )
        }
    }
}

@Composable
fun LokasiCard(
    lokasi: Lokasi,
    modifier: Modifier = Modifier,
    onDeleteClick: (Lokasi) -> Unit
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
        )
        {
            Row(
                modifier = Modifier.
                fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically)
            {
                Text(text = "Id Lokasi: ${lokasi.id_lokasi}", style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(lokasi) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                    )
                }

            }
            Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f), thickness = 1.dp)
            Spacer(modifier = Modifier.height(1.dp))
            Text(text = lokasi.nama_lokasi, style = MaterialTheme.typography.bodyMedium,)
            Text(text = "Alamat: ${lokasi.alamat_lokasi}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Kapasitas: ${lokasi.kapasitas}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}