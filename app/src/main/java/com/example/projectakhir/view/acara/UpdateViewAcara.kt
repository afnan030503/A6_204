package com.example.projectakhir.view.acara

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir.PenyediaViewModel
import com.example.projectakhir.cmwidget.CustomTopAppBar
import com.example.projectakhir.navigasi.DestinasiNavigasi
import com.example.projectakhir.viewmodel.acara.UpdateViewModelAcara
import com.example.projectakhir.viewmodel.acara.toAcara
import com.example.projectakhir.viewmodel.klien.HomeKlienUiState
import com.example.projectakhir.viewmodel.klien.HomeViewModelKlien
import com.example.projectakhir.viewmodel.lokasi.HomeLokasiUiState
import com.example.projectakhir.viewmodel.lokasi.HomeViewModelLokasi
import kotlinx.coroutines.launch
import java.util.Calendar

object DestinasiUpdateAcara : DestinasiNavigasi {
    override val route = "update_acara"
    const val ID_ACARA = "id_acara"
    val routesWithArg = "$route/{$ID_ACARA}"
    override val titleRes = "Update acara"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateViewAcara(
    id_acara: Int,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateViewModelAcara = viewModel(factory = PenyediaViewModel.Factory),
    klienViewModel: HomeViewModelKlien = viewModel(factory = PenyediaViewModel.Factory),
    lokasiViewModel: HomeViewModelLokasi = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    // Ambil state dari ViewModel
    val uiState = viewModel.uiState.value

    val klienUiState = klienViewModel.klienUiState
    val selectedKlien by remember { mutableStateOf(klienViewModel.selectedKlien) }

    val lokasiUiState =lokasiViewModel.lokasiUiState
    val selectedLokasi by remember { mutableStateOf(lokasiViewModel.selectedLokasi) }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiUpdateAcara.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { padding ->
        val scrolState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(scrolState)
                .padding(1.dp)
        ) {
            EntryAcaraBody(
                insertUiState = uiState,
                klienUiState = klienUiState,
                selectedKlien = selectedKlien,
                onKlienSelected = {  klienViewModel.selectedKlien(it)  },
                lokasiUiState = lokasiUiState,
                selectedLokasi = selectedLokasi,
                onLokasiSelected = {  lokasiViewModel.selectedLokasi(it)  },
                onValueChange = {updatevalue ->
                    viewModel.updateAcaraState(updatevalue)
                },
                onAcaraValueChange = viewModel::updateAcaraState,
                onSaveClick = {
                    coroutineScope.launch {
                        val updatedAcara = uiState.insertUiEvent?.toAcara()?.copy(
                            id_klien = selectedKlien,
                            id_lokasi = selectedLokasi
                        )
                        updatedAcara?.let {
                            viewModel.updateAcara(id_acara = id_acara, acara = it)
                            navigateBack()
                        }
                    }
                }

            )
        }
    }
}