package com.example.projectakhir.view.acara

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.* // Import semua dari jetpack compose runtime
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir.cmwidget.CustomTopAppBar
import com.example.projectakhir.cmwidget.DropDown
import com.example.projectakhir.navigasi.DestinasiNavigasi
import com.example.projectakhir.viewmodel.acara.UpdateViewModelAcara
import com.example.projectakhir.viewmodel.acara.toAcara
import kotlinx.coroutines.launch

object DestinasiUpdateAcara : DestinasiNavigasi {
    override val route = "entry_klien"
    override val titleRes = "Tambah Klien"
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateViewAcara(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateViewModelAcara = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    // Collect UI state dari ViewModel
    val uiState by viewModel.uiState
    val klienList by viewModel.klienList
    val lokasiList by viewModel.lokasiList

    // State untuk mengontrol dropdown
    var klienExpanded by remember { mutableStateOf(false) }
    var lokasiExpanded by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        topBar = {
            CustomTopAppBar(
                title = "Update Acara",
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Input untuk nama acara
            OutlinedTextField(
                value = uiState.insertUiEvent.nama_acara,
                onValueChange = { updatedValue ->
                    viewModel.updateAcaraState(
                        uiState.insertUiEvent.copy(nama_acara = updatedValue)
                    )
                },
                label = { Text("Nama Acara") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            )

            // Dropdown untuk memilih klien
            DropDown(
                label = "Pilih Klien",
                items = klienList.map { "${it.id_klien} - ${it.nama_klien}" },
                selectedItem = klienList.find { it.id_klien == uiState.insertUiEvent.id_klien }
                    ?.let { "${it.id_klien} - ${it.nama_klien}" }
                    .orEmpty(),
                onItemSelected = { selectedIndex ->
                    val selectedKlien = klienList[selectedIndex]
                    viewModel.updateAcaraState(uiState.insertUiEvent.copy(id_klien = selectedKlien.id_klien))
                },
                expanded = klienExpanded,
                onExpandedChange = { klienExpanded = it }
            )

            // Dropdown untuk memilih lokasi
            DropDown(
                label = "Pilih Lokasi",
                items = lokasiList.map { "${it.id_lokasi} - ${it.nama_lokasi}" },
                selectedItem = lokasiList.find { it.id_lokasi == uiState.insertUiEvent.id_lokasi }
                    ?.let { "${it.id_lokasi} - ${it.nama_lokasi}" }
                    .orEmpty(),
                onItemSelected = { selectedIndex ->
                    val selectedLokasi = lokasiList[selectedIndex]
                    viewModel.updateAcaraState(uiState.insertUiEvent.copy(id_lokasi = selectedLokasi.id_lokasi))
                },
                expanded = lokasiExpanded,
                onExpandedChange = { lokasiExpanded = it }
            )

            // Tombol untuk menyimpan perubahan
            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.updateAcara(viewModel.idAcara, uiState.insertUiEvent.toAcara())
                        navigateBack() // Navigasi kembali setelah diperbarui
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
            ) {
                Text("Simpan")
            }
        }
    }
}