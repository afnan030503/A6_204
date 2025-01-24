package com.example.projectakhir.view.klien



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
import com.example.projectakhir.viewmodel.klien.UpdateViewModelKlien
import com.example.projectakhir.viewmodel.klien.toKlien
import kotlinx.coroutines.launch


object DestinasiUpdateKlien : DestinasiNavigasi {
    override val route = "update_klien"
    const val ID_KLIEN = "id_klien"
    val routesWithArg = "$route/{$ID_KLIEN}"
    override val titleRes = "Update Klien"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateViewKlien(
    id_klien: Int,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateViewModelKlien = viewModel(factory = PenyediaViewModel.Factory),

) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    // Collect the UI state from the ViewModel
    val uiState = viewModel.uiState.value

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiUpdateKlien.titleRes,
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
            EntryKlienBody(
                insertUiState = uiState,
                onKlienValueChange = { updatedValue ->
                    viewModel.updateKlienState(updatedValue) // Update ViewModel state
                },
                onSaveClick = {
                    uiState.insertUiEvent?.let { insertUiEvent ->
                        coroutineScope.launch {
                            // Call ViewModel update method
                            viewModel.updateKlien(
                                id_klien = viewModel.id_klien, // Pass the ID_KLIEN from ViewModel
                                klien = insertUiEvent.toKlien() // Convert UpdateUiEvent to Klien
                            )
                            navigateBack() // Navigate back after saving
                        }
                    }
                }
            )
        }
    }
}

