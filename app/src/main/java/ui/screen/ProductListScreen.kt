package ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.viewmodel.ProductViewModel


@Composable
fun ProductListScreen(viewModel: ProductViewModel, modifier: Modifier) {
    val products by viewModel.products.collectAsState()

    // Llamar a cargar productos solo una vez
    LaunchedEffect(Unit) {
        viewModel.loadProducts()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Lista de Productos",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        when {
            products.isEmpty() -> {
                Text(
                    text = "No hay productos disponibles",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray
                )
            }
            else -> {
                LazyColumn {
                    items(products) { product ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(text = product.name, style = MaterialTheme.typography.titleMedium)
                                Text(text = "Categoría: ${product.category}")
                                Text(text = "Precio: Q${product.price}")
                            }
                        }
                    }
                }
            }
        }
    }
}



