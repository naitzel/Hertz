package br.com.naitzel.hertz.presentation.ui.screens.crashed

import androidx.lifecycle.ViewModel
import br.com.naitzel.hertz.core.manager.CrashedManager
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class CrashedViewModel @Inject constructor(private val crashedManager: CrashedManager) :
    ViewModel() {

    private val _details = MutableStateFlow<String?>(null)

    val details = _details.asStateFlow()

    fun readDetails() {
        _details.value = crashedManager.details()
    }

    fun clearCrashed() {
        crashedManager.clear()
    }
}