package com.example.projectakhir.cmwidget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DatePickerField(
    label: String,
    selectedDate: String,
    onDateSelected: (String) -> Unit,
    onDatePickerClick: () -> Unit
) {
    OutlinedTextField(
        value = selectedDate,
        onValueChange = { onDateSelected(it) },
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        trailingIcon = {
            IconButton(onClick = onDatePickerClick) {
                Text("Pilih")
            }
        }
    )
}
