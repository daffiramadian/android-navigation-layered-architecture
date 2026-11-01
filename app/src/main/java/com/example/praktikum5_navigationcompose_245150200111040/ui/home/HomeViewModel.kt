package com.example.praktikum5_navigationcompose_245150200111040.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.praktikum5_navigationcompose_245150200111040.data.repository.StudentRepository

class HomeViewModel(
    private val repository: StudentRepository
) : ViewModel() {

    var uiState by mutableStateOf(HomeUiState())
        private set

    init {
        loadStudents()
    }

    fun loadStudents() {
        uiState = uiState.copy(isLoading = true, errorMessage = null)

        repository.getStudents { result ->
            result
                .onSuccess { data ->
                    uiState = uiState.copy(students = data, isLoading = false, errorMessage = null)
                }
                .onFailure { error ->
                    uiState = uiState.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "Gagal mengambil data",
                        students = emptyList()
                    )
                }
        }
    }

    fun onSearchQueryChange(query: String) {
        uiState = uiState.copy(searchQuery = query)
    }

    fun search() {
        val query = uiState.searchQuery.trim()
        if (query.isEmpty()) {
            loadStudents()
            return
        }

        uiState = uiState.copy(isLoading = true, errorMessage = null)

        repository.searchStudents(query) { result ->
            result
                .onSuccess { data ->
                    uiState = uiState.copy(students = data, isLoading = false, errorMessage = null)
                }
                .onFailure { error ->
                    uiState = uiState.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "Pencarian gagal",
                        students = emptyList()
                    )
                }
        }
    }

    companion object {
        fun factory(repository: StudentRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return HomeViewModel(repository) as T
                }
            }
    }
}
