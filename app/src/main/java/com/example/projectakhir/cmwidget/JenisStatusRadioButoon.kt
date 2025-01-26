package com.example.projectakhir.cmwidget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.projectakhir.viewmodel.acara.JenisStatus
import com.example.projectakhir.viewmodel.vendor.JenisVendor

@Composable
fun JenisVendorRadioButton(
    selectedJenisVendor: String,
    onJenisVendorChange: (String) -> Unit
) {
    // Semua nilai enum JenisVendor
    val jenisVendorOptions = JenisVendor.values()

    Column {
        Text(
            text = "Jenis Layanan Vendor",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 30.dp)
        )

        // Menampilkan RadioButton untuk setiap pilihan jenis vendor
        jenisVendorOptions.forEach { jenisVendor ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable { onJenisVendorChange(jenisVendor.displayNameVendor) }
            ) {
                RadioButton(
                    selected = selectedJenisVendor == jenisVendor.displayNameVendor,
                    onClick = { onJenisVendorChange(jenisVendor.displayNameVendor) }
                )
                Text(
                    text = jenisVendor.displayNameVendor,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}
