package br.com.naitzel.hertz.core.viewModels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.naitzel.hertz.core.manager.ConfigurationManager
import br.com.naitzel.hertz.core.manager.CrashedManager
import br.com.naitzel.hertz.core.navigation.DialogState
import br.com.naitzel.hertz.core.navigation.NavigationDestination
import br.com.naitzel.hertz.core.navigation.NavigationEffect
import br.com.naitzel.hertz.core.navigation.NavigationState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel para controlar a navegação e o estado da aplicação.
 */
@HiltViewModel
class NavigationViewModel @Inject constructor(
    private val crashedManager: CrashedManager,
    private val configurationManager: ConfigurationManager,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    // Navegação e estado da aplicação
    private val _navigationState = MutableStateFlow<NavigationState>(NavigationState.Loading)
    val navigationState: StateFlow<NavigationState> = _navigationState.asStateFlow()

    // Efeitos de navegação
    private val _navigationEffect = MutableSharedFlow<NavigationEffect>()
    val navigationEffect: SharedFlow<NavigationEffect> = _navigationEffect.asSharedFlow()

    // Diálogos
    private val _dialogState = MutableStateFlow<DialogState?>(null)
    val dialogState: StateFlow<DialogState?> = _dialogState.asStateFlow()

    private val isActive = false

    // Controle de sessão
    private var sessionTimeoutJob: Job? = null
    private var lastActivityTime = System.currentTimeMillis()

    /**
     * Inicializa a ViewModel.
     */
    init {
        initializeApp()
        startSessionTimeout()
    }

    /**
     * Inicializa a aplicação.
     */
    private fun initializeApp() {
        onInitialize()
    }

    // ========== NAVEGAÇÃO PRINCIPAL ==========

    fun onInitialize() {
        viewModelScope.launch {
            try {
                _navigationState.value = NavigationState.Loading

                // Verificar se a aplicação está com problemas
                val isCrashed = crashedManager.isCrashed()

                if (isCrashed) {
                    navigateToCrashed()
                } else {
                    navigateToHome()
                }

            } catch (e: Exception) {
                _navigationState.value = NavigationState.Error(e.message ?: "Erro na inicialização")
            }
        }
    }

    fun navigateToCrashed() {
        _navigationState.value = NavigationState.Crashed
        _navigationEffect.tryEmit(NavigationEffect.ClearBackStack)
    }

    fun navigateToHome() {
        updateActivity()
        _navigationState.value = NavigationState.Home
//        _navigationEffect.tryEmit(NavigationEffect.ClearBackStack)
    }

    fun navigateToList() {
        updateActivity()
        _navigationState.value = NavigationState.ServiceOrderList
//        _navigationEffect.tryEmit(NavigationEffect.ClearBackStack)
    }

    fun navigateToCreate() {
        updateActivity()
        _navigationState.value = NavigationState.ServiceOrderCreate
//        _navigationEffect.tryEmit(NavigationEffect.ClearBackStack)
    }

    fun navigateToEdit() {
        updateActivity()
        _navigationState.value = NavigationState.ServiceOrderEdit
        _navigationEffect.tryEmit(NavigationEffect.ClearBackStack)
    }

    // ========== CONTROLE DE SESSÃO ==========
    fun updateActivity() {
        lastActivityTime = System.currentTimeMillis()
        restartSessionTimeout()
    }

    private fun startSessionTimeout() {
        sessionTimeoutJob = viewModelScope.launch {
            while (isActive) {
                delay(30_000) // Verifica a cada 30 segundos

                val inactiveTime = System.currentTimeMillis() - lastActivityTime
                val timeoutLimit = 5 * 60 * 1000 // 5 minutos

                if (inactiveTime > timeoutLimit) {
                    handleSessionTimeout()
                    break
                }
            }
        }
    }

    private fun restartSessionTimeout() {
        sessionTimeoutJob?.cancel()
        startSessionTimeout()
    }

    private fun handleSessionTimeout() {
        showDialog(
            DialogState.SessionTimeout(
                onConfirm = { /*navigateToHome()*/ },
                onLogout = { /*logout()*/ }
            )
        )
    }

    // ========== CONTROLE DE DIÁLOGOS ==========

    fun showDialog(dialog: DialogState) {
        _dialogState.value = dialog
    }

    fun dismissDialog() {
        _dialogState.value = null
    }

    // ========== NAVEGAÇÃO COM VALIDAÇÕES ==========

    fun navigateWithValidation(destination: NavigationDestination) {
        updateActivity()

        when (destination) {
            else -> {
                // Navegar normalmente para outros destinos
            }
        }
    }

    // ========== CLEANUP ==========
    override fun onCleared() {
        super.onCleared()
        sessionTimeoutJob?.cancel()
    }
}