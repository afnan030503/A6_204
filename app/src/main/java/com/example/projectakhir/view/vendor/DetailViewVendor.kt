package com.example.projectakhir.view.vendor
import com.example.projectakhir.navigasi.DestinasiNavigasi
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir.PenyediaViewModel
import com.example.projectakhir.viewmodel.vendor.DetailViewModelVendor
import com.example.projectakhir.cmwidget.CustomTopAppBar
import com.example.projectakhir.model.Vendor
import com.example.projectakhir.view.klien.OnError
import com.example.projectakhir.view.klien.OnLoading
import com.example.projectakhir.viewmodel.vendor.DetailVendorUiState


object DestinasiDetailVendor : DestinasiNavigasi {
    override val route = "detail_vendor"
    override val titleRes = "Detail Vendor"
    const val ID_VENDOR = "id_vendor"
    val routesWithArg = "$route/{$ID_VENDOR}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailViewVendor(
    id_vendor: Int,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModelVendor = viewModel(factory = PenyediaViewModel.Factory),
    onEditClick: () -> Unit = {},
    navigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = DestinasiDetailVendor.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = { viewModel.getDetailVendorById() } // Trigger refresh action
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEditClick() },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(17.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Vendor"
                )
            }
        }
    ) { innerPadding ->
        BodyDetailVendor(
            modifier = Modifier.padding(innerPadding),
            detailUiState = viewModel.vendorDetailState,
            retryAction = { viewModel.getDetailVendorById() }
        )
    }
}
@Composable
fun BodyDetailVendor(
    modifier: Modifier = Modifier,
    detailUiState: DetailVendorUiState,
    retryAction: () -> Unit = {}
) {
    when (detailUiState) {
        is DetailVendorUiState.Loading -> {
            OnLoading(modifier = modifier.fillMaxSize())
        }
        is DetailVendorUiState.Success -> {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(17.dp)
            ) {
                ItemDetailVendor(vendor = detailUiState.vendor)
            }
        }
        is DetailVendorUiState.Error -> {
            OnError(
                retryAction = retryAction,
                modifier = modifier.fillMaxSize()
            )
        }
    }
}
@Composable
fun ItemDetailVendor(
    vendor: Vendor
) {
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
            ComponentDetailVendor(judul = "Nama Vendor", isinya = vendor.nama_vendor)
            Spacer(modifier = Modifier.padding(5.dp))
            ComponentDetailVendor(judul = "Jenis Vendor", isinya = vendor.jenis_vendor)
            Spacer(modifier = Modifier.padding(5.dp))
            ComponentDetailVendor(judul = "Kontak Vendor", isinya = vendor.kontak_vendor)
        }
    }
}
@Composable
fun ComponentDetailVendor(
    judul: String,
    isinya: String
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "$judul:",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = isinya,
            fontSize = 18.sp,
            fontWeight = FontWeight.Light
        )
    }
}