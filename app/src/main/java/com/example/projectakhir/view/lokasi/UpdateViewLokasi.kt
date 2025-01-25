package com.example.projectakhir.view.lokasi

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir.PenyediaViewModel
import com.example.projectakhir.cmwidget.CustomTopAppBar
import com.example.projectakhir.navigasi.DestinasiNavigasi
import com.example.projectakhir.viewmodel.lokasi.UpdateViewModelLokasi
import com.example.projectakhir.viewmodel.lokasi.toLokasi
import kotlinx.coroutines.launch

object DestinasiUpdateLokasi : DestinasiNavigasi {
    override val route = "update_lokasi"
    const val ID_LOKASI = "id_lokasi"
    val routesWithArg = "$route/{$ID_LOKASI}"
    override val titleRes = "Update Lokasi"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateViewLokasi(
    id_lokasi : Int,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateViewModelLokasi = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    // Collect the UI state from the ViewModel
    val uiState = viewModel.uiState.value

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiUpdateLokasi.titleRes,
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
                .padding(17.dp)
        ) {
            // Pass the UI state to the EntryBody
            EntryLokasiBody(
                insertUiState = uiState,
                onLokasiValueChange = { updatedValue ->
                    viewModel.updateLokasiState(updatedValue) // Update ViewModel state
                },
                onSaveClick = {
                    uiState.insertUiEvent?.let { insertUiEvent ->
                        coroutineScope.launch {
                            // Call ViewModel update method
                            viewModel.updateLokasi(
                                id_lokasi = viewModel.id_lokasi, // Pass the ID_LOKASI from ViewModel
                                lokasi = insertUiEvent.toLokasi() // Convert UpdateUiEvent to Lokasi
                            )
                            navigateBack() // Navigate back after saving
                        }
                    }
                }
            )
        }
    }
}
