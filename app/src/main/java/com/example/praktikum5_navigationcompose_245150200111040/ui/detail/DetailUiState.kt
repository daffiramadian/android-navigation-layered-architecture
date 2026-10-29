package com.example.praktikum5_navigationcompose_245150200111040.ui.detail

import com.example.praktikum5_navigationcompose_245150200111040.data.model.Student

data class DetailUiState(
    val student: Student? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val requestedId: Int? = null
)
