package com.example.catfacts.ui

sealed class CatFactUiState {
    data object Idle : CatFactUiState()
    data object Loading : CatFactUiState()
    data class Success(val fact: String) : CatFactUiState()
    data class Error(val message: String) : CatFactUiState()
}
