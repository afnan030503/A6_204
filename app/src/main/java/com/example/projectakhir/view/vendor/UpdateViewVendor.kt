package com.example.projectakhir.view.vendor

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
import com.example.projectakhir.viewmodel.vendor.UpdateViewModelVendor
import com.example.projectakhir.viewmodel.vendor.toVendor
import kotlinx.coroutines.launch

object DestinasiUpdateVendor : DestinasiNavigasi {
    override val route = "update_vendor"
    const val ID_VENDOR = "id_vendor"
    val routesWithArg = "$route/{$ID_VENDOR}"
    override val titleRes = "Update Vendor"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateViewVendor(
    id_vendor: Int,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateViewModelVendor = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    // Collect the UI state from the ViewModel
    val uiState = viewModel.uiState.value

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiUpdateVendor.titleRes,
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
            EntryVendorBody(
                insertUiState = uiState,
                onVendorValueChange = { updatedValue ->
                    viewModel.updateVendorState(updatedValue) // Update ViewModel state
                },
                onSaveClick = {
                    uiState.insertUiEvent?.let { insertUiEvent ->
                        coroutineScope.launch {
                            // Call ViewModel update method
                            viewModel.updateVendor(
                                id_vendor = viewModel.id_vendor, // Pass the ID_VENDOR from ViewModel
                                vendor = insertUiEvent.toVendor() // Convert UpdateUiEvent to Vendor
                            )
                            navigateBack() // Navigate back after saving
                        }
                    }
                }
            )
        }
    }
}