package com.example.projectakhir.view.vendor

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

import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir.PenyediaViewModel
import com.example.projectakhir.cmwidget.CustomTopAppBar
import com.example.projectakhir.cmwidget.MenuButton
import com.example.projectakhir.model.Vendor
import com.example.projectakhir.navigasi.DestinasiNavigasi
import com.example.projectakhir.view.klien.OnError
import com.example.projectakhir.view.klien.OnLoading
import com.example.projectakhir.viewmodel.vendor.HomeVendorUiState
import com.example.projectakhir.viewmodel.vendor.HomeViewModelVendor

object DestinasiHomeVendor : DestinasiNavigasi {
    override val route = "vendor"
    override val titleRes = "Halaman Vendor"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenVendor(
    navigateToAddVendor: () -> Unit,
    navigateToAcara: () -> Unit,
    navigateToKlien: () -> Unit,
    navigateToLokasi: () -> Unit,
    navigateToVendor: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit = {},
    viewModel: HomeViewModelVendor = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiHomeVendor.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onRefresh = { viewModel.getVendor() }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToAddVendor,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Tambah Vendor")
            }
        },
        bottomBar = {
            MenuButton(
                onAcaraClick = navigateToAcara,
                onKlienClick = navigateToKlien,
                onLokasiClick = navigateToLokasi,
                onVendorClick = navigateToVendor,
            )
        }
    ) { innerPadding ->
        HomeStatusVendor(
            vendorUiState = viewModel.vendorUiState,
            retryAction = { viewModel.getVendor() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick, onDeleteClick = { viewModel.deleteVendor(it.id_vendor) }
        )
    }
}


@Composable
fun HomeStatusVendor(
    vendorUiState: HomeVendorUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Vendor) -> Unit,
    onDetailClick: (Int) -> Unit
) {
    when (vendorUiState) {
        is HomeVendorUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomeVendorUiState.Success ->
            if (vendorUiState.vendor.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada vendor")
                }
            } else {
                VendorLayout(
                    vendor = vendorUiState.vendor,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.id_vendor)
                    },
                    onDeleteClick = {
                        onDeleteClick(it)
                    }
                )
            }
            is HomeVendorUiState.Error -> OnError(retryAction = retryAction, modifier = modifier.fillMaxSize())
        }

    }

@Composable
fun VendorLayout(
    vendor: List<Vendor>,
    modifier: Modifier = Modifier,
    onDetailClick: (Vendor) -> Unit,
    onDeleteClick: (Vendor) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(vendor) { vendor ->
            VendorCard(
                vendor = vendor,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(vendor) },
                onDeleteClick = { onDeleteClick(vendor) }
            )
        }
    }
}

@Composable
fun VendorCard(
    vendor: Vendor,
    modifier: Modifier = Modifier,
    onDeleteClick: (Vendor) -> Unit
) {
    Card(
        modifier = modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .shadow(20.dp, shape = MaterialTheme.shapes.medium),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(Color.Transparent),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = vendor.nama_vendor,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(vendor) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                    )
                }
            }
            Text(text = "Kontak: ${vendor.kontak_vendor}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Jenis Vendor: ${vendor.jenis_vendor}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}