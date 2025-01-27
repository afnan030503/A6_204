package com.example.projectakhir.view.acara

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir.PenyediaViewModel
import com.example.projectakhir.cmwidget.DatePickerField
import com.example.projectakhir.cmwidget.DropDown
import com.example.projectakhir.cmwidget.SectionCard
import com.example.projectakhir.navigasi.DestinasiNavigasi
import kotlinx.coroutines.launch
import com.example.projectakhir.viewmodel.acara.InsertViewModelAcara
import java.util.*

object DestinasiAcaraEntry : DestinasiNavigasi {
    override val route = "entry_acara"
    override val titleRes = "Tambah Acara"
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryAcaraScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertViewModelAcara = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    // Langsung mengakses state dari ViewModel tanpa collectAsState()
    val uiState = viewModel.uiState.insertUiEvent

    // State untuk dropdown
    var klienExpanded by remember { mutableStateOf(false) }
    var lokasiExpanded by remember { mutableStateOf(false) }

    // Context untuk DatePicker
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tambah Acara") },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Informasi Acara

                OutlinedTextField(
                    value = uiState.nama_acara,
                    onValueChange = {
                        viewModel.updateInsertAcaraState(uiState.copy(nama_acara = it))
                    },
                    label = { Text("Nama Acara") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = uiState.deskripsi_acara,
                    onValueChange = {
                        viewModel.updateInsertAcaraState(uiState.copy(deskripsi_acara = it))
                    },
                    label = { Text("Deskripsi Acara") },
                    modifier = Modifier.fillMaxWidth()
                )


            // Detail Acara

                val klienList = viewModel.klienList.value
                DropDown(
                    label = "Pilih Klien",
                    items = klienList.map { "${it.id_klien}" },
                    selectedItem = klienList.find { it.id_klien == uiState.id_klien }
                        ?.let { "${it.id_klien}" }
                        .orEmpty(),
                    onItemSelected = { selectedIndex ->
                        val selectedKlien = klienList[selectedIndex]
                        viewModel.updateInsertAcaraState(uiState.copy(id_klien = selectedKlien.id_klien))
                    },
                    expanded = klienExpanded,
                    onExpandedChange = { klienExpanded = it }
                )

                val lokasiList = viewModel.lokasiList.value
                DropDown(
                    label = "Lokasi",
                    items = lokasiList.map { it.id_lokasi.toString() },
                    selectedItem = uiState.id_lokasi.toString(),
                    onItemSelected = { selectedLokasi ->
                        viewModel.updateInsertAcaraState(uiState.copy(id_lokasi = selectedLokasi.toInt()))
                    },
                    expanded = lokasiExpanded,
                    onExpandedChange = { lokasiExpanded = it }
                )


            // Tanggal Acara

                DatePickerField(
                    label = "Tanggal Mulai",
                    selectedDate = uiState.tanggal_mulai,
                    onDateSelected = { selectedDate ->
                        viewModel.updateInsertAcaraState(uiState.copy(tanggal_mulai = selectedDate))
                    },
                    onDatePickerClick = {
                        DatePickerDialog(
                            context,
                            { _, year, month, dayOfMonth ->
                                val selectedDate = "$dayOfMonth-${month + 1}-$year"
                                viewModel.updateInsertAcaraState(uiState.copy(tanggal_mulai = selectedDate))
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                        ).show()
                    }
                )
                DatePickerField(
                    label = "Tanggal Berakhir",
                    selectedDate = uiState.tanggal_berakhir,
                    onDateSelected = { selectedDate ->
                        viewModel.updateInsertAcaraState(uiState.copy(tanggal_berakhir = selectedDate))
                    },
                    onDatePickerClick = {
                        DatePickerDialog(
                            context,
                            { _, year, month, dayOfMonth ->
                                val selectedDate = "$dayOfMonth-${month + 1}-$year"
                                viewModel.updateInsertAcaraState(uiState.copy(tanggal_berakhir = selectedDate))
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                        ).show()
                    }
                )


            // Status Acara
            SectionCard(title = "Status Acara") {
                val statusOptions = listOf("Direncanakan", "Berlangsung", "Selesai")
                statusOptions.forEach { status ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = uiState.status_acara == status,
                            onClick = {
                                viewModel.updateInsertAcaraState(uiState.copy(status_acara = status))
                            }
                        )
                        Text(text = status, modifier = Modifier.padding(start = 8.dp))
                    }
                }
            }

            // Tombol Simpan
            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.insertAcara()
                        navigateBack()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                Text("Simpan")
            }
        }
    }
}
