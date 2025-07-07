package com.example.catfacts.ui

import kotlinx.coroutines.flow.StateFlow

interface ICatFactViewModel {
    val uiState: StateFlow<CatFactUiState>
    fun getCatFact()
}
