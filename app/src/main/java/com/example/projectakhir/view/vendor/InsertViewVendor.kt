package com.example.projectakhir.view.vendor

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir.PenyediaViewModel
import com.example.projectakhir.cmwidget.CustomTopAppBar
import com.example.projectakhir.cmwidget.JenisVendorRadioButton
import com.example.projectakhir.cmwidget.SectionCard
import com.example.projectakhir.navigasi.DestinasiNavigasi
import com.example.projectakhir.viewmodel.vendor.InsertVendorUiEvent
import com.example.projectakhir.viewmodel.vendor.InsertVendorUiState
import com.example.projectakhir.viewmodel.vendor.InsertViewModelVendor
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select

object DestinasiVendorEntry : DestinasiNavigasi {
    override val route = "entry_vendor"
    override val titleRes = "Tambah Vendor"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryVendorScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertViewModelVendor = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiVendorEntry.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryVendorBody(
            insertUiState = viewModel.uiState,
            onVendorValueChange = viewModel::updateInsertVendorState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertVendor()
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
fun EntryVendorBody(
    insertUiState: InsertVendorUiState,
    onVendorValueChange: (InsertVendorUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputVendor(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onVendorValueChange,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputVendor(
    insertUiEvent: InsertVendorUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertVendorUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SectionCard(title = "Informasi Acara") {
            OutlinedTextField(
                value = insertUiEvent.nama_vendor,
                onValueChange = { onValueChange(insertUiEvent.copy(nama_vendor = it)) },
                label = { Text("Nama Vendor", color = Color.White) },
                modifier = Modifier.fillMaxWidth(),
                enabled = enabled,
                singleLine = true
            )
        }
        SectionCard(title = "") {
            JenisVendorRadioButton(
                selectedJenisVendor = insertUiEvent.jenis_vendor,
                onJenisVendorChange = { selected ->
                    onValueChange(insertUiEvent.copy(jenis_vendor = selected))
                }
            )
        }
        SectionCard(title = "") {
            OutlinedTextField(
                value = insertUiEvent.kontak_vendor,
                onValueChange = { onValueChange(insertUiEvent.copy(kontak_vendor = it)) },
                label = { Text("Kontak Vendor", color = Color.White) },
                modifier = Modifier.fillMaxWidth(),
                enabled = enabled,
                singleLine = true
            )
        }
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