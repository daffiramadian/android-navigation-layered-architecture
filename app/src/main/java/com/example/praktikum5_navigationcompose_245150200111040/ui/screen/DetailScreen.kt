package com.example.praktikum5_navigationcompose_245150200111040.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.praktikum5_navigationcompose_245150200111040.ui.detail.DetailUiState

@Composable
fun DetailScreen(
    uiState: DetailUiState,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Detail Student", style = MaterialTheme.typography.headlineMedium)

        when {
            uiState.errorMessage != null -> {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.onErrorContainer
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Student Tidak Ditemukan",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (uiState.requestedId != null) {
                                "Data mahasiswa dengan ID ${uiState.requestedId} tidak terdaftar di sistem."
                            } else {
                                uiState.errorMessage
                            },
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = onBack,
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Kembali ke Home")
                        }
                    }
                }
            }

            uiState.student != null -> {
                val student = uiState.student
                Text("ID: ${student.id}")
                Text("Nama: ${student.name}")
                Text("Program: ${student.program}")
                Text("Angkatan: ${student.year}")

                Spacer(modifier = Modifier.height(4.dp))
                Button(onClick = onBack) {
                    Text("Kembali")
                }
            }
        }
    }
}
