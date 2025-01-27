package com.example.projectakhir.cmwidget

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StatusAcaraSection(
    title: String,
    currentStatus: String, // Status saat ini
    onStatusChange: (String) -> Unit // Callback untuk mengubah status
) {
    val statusOptions = listOf("Direncanakan", "Berlangsung", "Selesai")

    SectionCard(title = title) {
        statusOptions.forEach { status ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = currentStatus == status, // Cek apakah status ini dipilih
                    onClick = { onStatusChange(status) } // Ubah status saat RadioButton diklik
                )
                Text(text = status, modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}
