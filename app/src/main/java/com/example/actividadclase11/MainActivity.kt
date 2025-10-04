    package com.example.actividadclase11

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.actividadclase11.ui.theme.ProductApp
import data.network.ApiClient
import data.repository.ProductRepository
import ui.viewmodel.ProductViewModel
import ui.viewmodel.ProductViewModelFactory

    class MainActivity : ComponentActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            // Crea el repositorio fuera del setContent
            val repository = ProductRepository(ApiClient.productService)

            setContent {
                // Crea el ViewModel dentro de Compose y pásale el repositorio
                val viewModel: ProductViewModel = viewModel(
                    factory = ProductViewModelFactory(repository)
                )

                ProductApp(viewModel)
            }
        }
    }
