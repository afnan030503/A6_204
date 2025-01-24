package com.example.projectakhir.view.klien


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir.PenyediaViewModel
import com.example.projectakhir.cmwidget.CustomTopAppBar
import com.example.projectakhir.navigasi.DestinasiNavigasi
import com.example.projectakhir.viewmodel.klien.InsertKlienUiEvent
import com.example.projectakhir.viewmodel.klien.InsertKlienUiState
import com.example.projectakhir.viewmodel.klien.InsertViewModelKlien
import kotlinx.coroutines.launch

object DestinasiKlienEntry : DestinasiNavigasi {
    override val route = "entry_klien"
    override val titleRes = "Tambah Klien"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryKlienScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertViewModelKlien = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiKlienEntry.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryKlienBody(
            insertUiState = viewModel.uiState,
            onKlienValueChange = viewModel::updateInsertKlienState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertKlien()
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
fun EntryKlienBody(
    insertUiState: InsertKlienUiState,
    onKlienValueChange: (InsertKlienUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputKlien(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onKlienValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputKlien(
    insertUiEvent: InsertKlienUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertKlienUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertUiEvent.nama_klien,
            onValueChange = { onValueChange(insertUiEvent.copy(nama_klien = it)) },
            label = { Text("Nama Klien") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.kontak_klien,
            onValueChange = { onValueChange(insertUiEvent.copy(kontak_klien = it)) },
            label = { Text("Kontak Klien") },
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
