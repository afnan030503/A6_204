package com.example.projectakhir.view.lokasi

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir.PenyediaViewModel
import com.example.projectakhir.cmwidget.CustomTopAppBar
import com.example.projectakhir.cmwidget.SectionCard
import com.example.projectakhir.navigasi.DestinasiNavigasi
import com.example.projectakhir.viewmodel.lokasi.InsertLokasiUiEvent
import com.example.projectakhir.viewmodel.lokasi.InsertLokasiUiState
import com.example.projectakhir.viewmodel.lokasi.InsertViewModelLokasi
import kotlinx.coroutines.launch

object DestinasiLokasiEntry : DestinasiNavigasi {
    override val route = "entry_lokasi"
    override val titleRes = "Tambah Lokasi"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryLokasiScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertViewModelLokasi = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiLokasiEntry.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryLokasiBody(
            insertUiState = viewModel.uiState,
            onLokasiValueChange = viewModel::updateInsertLokasiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertLokasi()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryLokasiBody(
    insertUiState: InsertLokasiUiState,
    onLokasiValueChange: (InsertLokasiUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputLokasi(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onLokasiValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth(),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = Color(0xFF585E70), // Warna silver
                contentColor = Color.Black // Warna teks di tombol
            )
        ) {
            Text(text = "Simpan")
        }
    }
}

@Composable
fun FormInputLokasi(
    insertUiEvent: InsertLokasiUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertLokasiUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SectionCard(title = "") {
            OutlinedTextField(
                value = insertUiEvent.nama_lokasi,
                onValueChange = { onValueChange(insertUiEvent.copy(nama_lokasi = it)) },
                label = { Text("Nama Lokasi") },
                modifier = Modifier.fillMaxWidth(),
                enabled = enabled,
                singleLine = true
            )
            OutlinedTextField(
                value = insertUiEvent.alamat_lokasi,
                onValueChange = { onValueChange(insertUiEvent.copy(alamat_lokasi = it)) },
                label = { Text("Alamat Lokasi") },
                modifier = Modifier.fillMaxWidth(),
                enabled = enabled
            )
            OutlinedTextField(
                value = insertUiEvent.kapasitas.toString(), // Konversi Int ke String
                onValueChange = {
                    val kapasitasBaru =
                        it.toIntOrNull() ?: 0 // Konversi String ke Int, default 0 jika null
                    onValueChange(insertUiEvent.copy(kapasitas = kapasitasBaru))
                },
                label = { Text("Kapasitas") },
                modifier = Modifier.fillMaxWidth(),
                enabled = enabled,
                singleLine = true
            )
            if (enabled) {
                Text(
                    text = "Isi Semua Data!",
                    modifier = Modifier.padding(12.dp)
                )
            }
            Divider(
                thickness = 8.dp,
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}