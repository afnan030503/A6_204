package com.example.projectakhir.view.acara

import CalendarDatePicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir.PenyediaViewModel
import com.example.projectakhir.cmwidget.CustomTopAppBar
import com.example.projectakhir.cmwidget.DropDown
import com.example.projectakhir.cmwidget.SectionCard
import com.example.projectakhir.navigasi.DestinasiNavigasi
import com.example.projectakhir.viewmodel.acara.InsertAcaraUiEvent
import com.example.projectakhir.viewmodel.acara.InsertAcaraUiState
import com.example.projectakhir.viewmodel.acara.InsertViewModelAcara
import com.example.projectakhir.viewmodel.klien.HomeKlienUiState
import com.example.projectakhir.viewmodel.klien.HomeViewModelKlien
import com.example.projectakhir.viewmodel.lokasi.HomeLokasiUiState
import com.example.projectakhir.viewmodel.lokasi.HomeViewModelLokasi
import kotlinx.coroutines.launch

object DestinasiAcaraEntry : DestinasiNavigasi {
    override val route = "entry_acara"
    override val titleRes = "Tambah Acara"
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryAcaraScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertViewModelAcara = viewModel(factory = PenyediaViewModel.Factory),
    klienViewModel: HomeViewModelKlien = viewModel(factory = PenyediaViewModel.Factory),
    lokasiViewModel: HomeViewModelLokasi = viewModel(factory = PenyediaViewModel.Factory),

) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val klienUiState = klienViewModel.klienUiState
    val selectedKlien by remember { mutableStateOf(klienViewModel.selectedKlien) }

    val lokasiUiState =lokasiViewModel.lokasiUiState
    val selectedLokasi by remember { mutableStateOf(lokasiViewModel.selectedLokasi) }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiAcaraEntry.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack,
            )
        }
    ) { innerPadding ->
        EntryAcaraBody(
            insertUiState = viewModel.uiState,
            klienUiState = klienUiState,
            selectedKlien = selectedKlien,
            onKlienSelected = {  klienViewModel.selectedKlien(it)  },
            lokasiUiState = lokasiUiState,
            selectedLokasi = selectedLokasi,
            onLokasiSelected = {  lokasiViewModel.selectedLokasi(it)  },
            onValueChange = viewModel::updateInsertAcaraState,
            onAcaraValueChange = viewModel::updateInsertAcaraState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertAcara()
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
fun EntryAcaraBody(
    insertUiState: InsertAcaraUiState,
    klienUiState: HomeKlienUiState,
    selectedKlien: Int,
    onKlienSelected: (Int) -> Unit,
    lokasiUiState: HomeLokasiUiState,
    selectedLokasi: Int,
    onLokasiSelected: (Int) -> Unit,
    onValueChange: (InsertAcaraUiEvent) -> Unit = {},
    onAcaraValueChange: (InsertAcaraUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputAcara(
            insertUiEvent = insertUiState.insertUiEvent,
            klienUiState = klienUiState,
            selectedKlien = selectedKlien,
            onKlienSelected = onKlienSelected,
            lokasiUiState = lokasiUiState,
            selectedLokasi = selectedLokasi,
            onLokasiSelected = onLokasiSelected,
            onValueChange = onValueChange,
            onStatusChange = { newStatus ->
                onAcaraValueChange(insertUiState.insertUiEvent.copy(status_acara = newStatus))
            },
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
fun FormInputAcara(
    insertUiEvent: InsertAcaraUiEvent,
    klienUiState: HomeKlienUiState,
    selectedKlien: Int,
    onKlienSelected: (Int) -> Unit,
    lokasiUiState: HomeLokasiUiState,
    selectedLokasi: Int,
    onLokasiSelected: (Int) -> Unit,
    onValueChange: (InsertAcaraUiEvent) -> Unit = {},
    onStatusChange: (String) -> Unit = {},
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SectionCard(title = "") {
            OutlinedTextField(
                value = insertUiEvent.nama_acara,
                onValueChange = { onValueChange(insertUiEvent.copy(nama_acara = it)) },
                label = { Text("Nama Acara", color = Color.White) },
                modifier = Modifier.fillMaxWidth(),
                enabled = enabled,
                singleLine = true,

            )
            OutlinedTextField(
                value = insertUiEvent.deskripsi_acara,
                onValueChange = { onValueChange(insertUiEvent.copy(deskripsi_acara = it)) },
                label = { Text("Deskripsi Acara", color = Color.White) },
                modifier = Modifier.fillMaxWidth(),
                enabled = enabled,
                singleLine = true
            )
            CalendarDatePicker(
                label = "Tanggal Mulai",
                selectedDate = insertUiEvent.tanggal_mulai,
                onDateSelected = { selectedDate ->
                    onValueChange(insertUiEvent.copy(tanggal_mulai = selectedDate))
                }
            )

            CalendarDatePicker(
                label = "Tanggal Berakhir",
                selectedDate = insertUiEvent.tanggal_berakhir,
                onDateSelected = { selectedDate ->
                    onValueChange(insertUiEvent.copy(tanggal_berakhir = selectedDate))
                }
            )


            when (klienUiState) {
                is HomeKlienUiState.Loading -> {
                    Text("Memuat data klien...")
                }

                is HomeKlienUiState.Error -> {
                    Text("Gagal memuat data klien.")
                }

                is HomeKlienUiState.Success -> {
                    DropDown(
                        tittle = "Pilih Klien",
                        options = klienUiState.klien.map { it.id_klien },
                        selectedOption = selectedKlien,
                        onOptionSelected = { id_klien ->
                            onKlienSelected(id_klien)
                            onValueChange(insertUiEvent.copy(id_klien = id_klien))
                        }
                    )
                }
            }
            when (lokasiUiState) {
                is HomeLokasiUiState.Loading -> {
                    Text("Memuat data lokasi...")
                }

                is HomeLokasiUiState.Error -> {
                    Text("Gagal memuat data lokasi.")
                }

                is HomeLokasiUiState.Success -> {
                    DropDown(
                        tittle = "Pilih Klien",
                        options = lokasiUiState.lokasi.map { it.id_lokasi },
                        selectedOption = selectedLokasi,
                        onOptionSelected = { id_lokasi ->
                            onLokasiSelected(id_lokasi)
                            onValueChange(insertUiEvent.copy(id_lokasi = id_lokasi))
                        }
                    )
                }
            }
        }
            // Status Acara
            SectionCard(title = "Status Acara") {
                val statusOptions = listOf("Direncanakan", "Berlangsung", "Selesai")
                statusOptions.forEach { status ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = insertUiEvent.status_acara == status,
                            onClick = {
                                onStatusChange(status)
                            }
                        )
                        Text(text = status, modifier = Modifier.padding(start = 9.dp))
                    }
                }
            }

        }

    }
