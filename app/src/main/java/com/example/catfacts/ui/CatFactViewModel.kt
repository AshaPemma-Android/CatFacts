package com.example.catfacts.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catfacts.domain.usecase.GetCatFactUseCase
import com.example.catfacts.util.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatFactViewModel @Inject constructor(
    private val getCatFactUseCase: GetCatFactUseCase,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel(), ICatFactViewModel {

    private val _uiState = MutableStateFlow<CatFactUiState>(CatFactUiState.Idle)
    override val uiState: StateFlow<CatFactUiState> = _uiState

    override fun getCatFact() {
        viewModelScope.launch(dispatcherProvider.io()) {
            _uiState.value = CatFactUiState.Loading
            try {
                val fact = getCatFactUseCase()
                _uiState.value = CatFactUiState.Success(fact.fact)
            } catch (e: Exception) {
                _uiState.value = CatFactUiState.Error("Failed to load cat fact.")
            }
        }
    }
}
