package com.example.projectakhir.cmwidget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun MenuButton(
    modifier: Modifier = Modifier,
    onAcaraClick: () -> Unit,
    onKlienClick: () -> Unit,
    onLokasiClick: () -> Unit,
    onVendorClick: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(
                color = Color(0xFF3949AB), // Warna biru solid
                shape = RoundedCornerShape(8.dp) // Sudut lebih kecil
            )
            .padding(8.dp)
    ) {
        // Tombol Acara
        IconButton(onClick = onAcaraClick) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Filled.MailOutline,
                    contentDescription = "Acara",
                    tint = Color.White
                )
                Text(text = "Acara", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }

        // Tombol Klien
        IconButton(onClick = onKlienClick) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Filled.List,
                    contentDescription = "Klien",
                    tint = Color.White
                )
                Text(text = "Klien", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }

        // Tombol Lokasi
        IconButton(onClick = onLokasiClick) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Filled.AccountBox,
                    contentDescription = "Lokasi",
                    tint = Color.White
                )
                Text(text = "Lokasi", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
        // Tombol Vendor
        IconButton(onClick = onVendorClick) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Vendor",
                    tint = Color.White
                )
                Text(text = "Vendor", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}