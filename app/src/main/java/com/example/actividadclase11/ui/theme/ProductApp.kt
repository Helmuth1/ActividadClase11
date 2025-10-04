package com.example.actividadclase11.ui.theme


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ui.screen.ProductListScreen
import ui.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductApp(viewModel: ProductViewModel) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Gestión de Productos") }) }
    ) { innerPadding ->
        ProductListScreen(viewModel = viewModel, modifier = Modifier.padding(innerPadding).fillMaxSize())
    }
}
