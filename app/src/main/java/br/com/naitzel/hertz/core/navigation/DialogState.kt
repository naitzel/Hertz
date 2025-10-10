package br.com.naitzel.hertz.core.navigation

sealed class DialogState {
    data class Error(
        val message: String,
        val onDismiss: () -> Unit = {}
    ) : DialogState()

    data class Confirmation(
        val title: String,
        val message: String,
        val onConfirm: () -> Unit,
        val onDismiss: () -> Unit = {}
    ) : DialogState()

    data class SessionTimeout(
        val onConfirm: () -> Unit,
        val onLogout: () -> Unit
    ) : DialogState()

    data class Loading(val message: String) : DialogState()
}