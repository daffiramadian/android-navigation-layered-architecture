package com.example.praktikum5_navigationcompose_245150200111040.ui.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.praktikum5_navigationcompose_245150200111040.data.repository.StudentRepository

class DetailViewModel(
    private val repository: StudentRepository
) : ViewModel() {

    var uiState by mutableStateOf(DetailUiState())
        private set

    fun loadStudent(id: Int) {
        uiState = uiState.copy(isLoading = true, errorMessage = null, requestedId = id)

        repository.getStudent(id) { result ->
            result
                .onSuccess { student ->
                    uiState = DetailUiState(student = student, requestedId = id)
                }
                .onFailure { error ->
                    uiState = DetailUiState(
                        errorMessage = error.message ?: "Data tidak tersedia",
                        requestedId = id
                    )
                }
        }
    }

    companion object {
        fun factory(repository: StudentRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return DetailViewModel(repository) as T
                }
            }
    }
}
