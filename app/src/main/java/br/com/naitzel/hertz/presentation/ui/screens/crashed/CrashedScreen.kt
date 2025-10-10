package br.com.naitzel.hertz.presentation.ui.screens.crashed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.naitzel.hertz.presentation.ui.theme.LocalColors
import br.com.naitzel.hertz.presentation.ui.theme.LocalSpacing

@Composable
fun CrashedScreen(
    viewModel: CrashedViewModel = hiltViewModel(),
    onToContinue: () -> Unit
) {
    val details = viewModel.details.collectAsState()
    val colors = LocalColors.current
    val spacing = LocalSpacing.current

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .background(colors.background)
                .padding(paddingValues)
                .fillMaxSize()
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(spacing.medium),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {
            Button(onClick = {
                viewModel.readDetails()
            }) {
                Text("Ler detalhes")
            }

            Button(onClick = {
                viewModel.clearCrashed()
                onToContinue()
            }) {
                Text("Continuar")
            }

            details.value?.let {
                Text(it)
            }
        }
    }
}
