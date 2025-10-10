package br.com.naitzel.hertz.presentation.ui.screens.home


sealed class HomeUiState {
    object Empty : HomeUiState()
    object Loading : HomeUiState()
}