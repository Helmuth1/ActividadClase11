package ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import data.model.Product
import data.model.ProductRequest
import ui.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsScreen(viewModel: ProductViewModel, modifier: Modifier = Modifier) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Estados locales para búsqueda y formulario
    var searchQuery by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.loadAll()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Gestión de Productos", style = MaterialTheme.typography.headlineSmall)

        Spacer(Modifier.height(12.dp))

        // 🔍 Barra de búsqueda
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Buscar por nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { viewModel.searchByName(searchQuery) }) {
                Text("Buscar")
            }
            Button(onClick = { viewModel.loadAll() }) {
                Text("Recargar")
            }
        }

        Spacer(Modifier.height(20.dp))

        // 📝 Formulario de creación
        Text("Nuevo Producto", style = MaterialTheme.typography.titleMedium)
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = price,
            onValueChange = { price = it },
            label = { Text("Precio") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = category,
            onValueChange = { category = it },
            label = { Text("Categoría") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val req = ProductRequest(name, price.toDoubleOrNull() ?: 0.0, category)
                viewModel.create(req)
                name = ""
                price = ""
                category = ""
            },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        ) {
            Text("Agregar Producto")
        }

        Spacer(Modifier.height(16.dp))

        // 📦 Lista de productos
        if (uiState.loading) {
            LinearProgressIndicator(Modifier.fillMaxWidth())
        }

        if (uiState.message != null) {
            Text(
                text = uiState.message ?: "",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(4.dp)
            )
        }

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(uiState.items) { product ->
                ProductItem(
                    product = product,
                    onUpdate = {
                        val newPrice = product.price + 1.0
                        viewModel.update(
                            product.id ?: return@ProductItem,
                            ProductRequest(product.name, newPrice, product.category)
                        )
                    },
                    onDelete = {
                        product.id?.let { viewModel.delete(it) }
                    }
                )
            }
        }
    }
}

@Composable
fun ProductItem(product: Product, onUpdate: () -> Unit, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(product.name, style = MaterialTheme.typography.titleMedium)
            Text("Precio: Q${product.price}")
            Text("Categoría: ${product.category ?: "N/A"}")

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = onUpdate) {
                    Text("+ Q1.00")
                }
                Button(onClick = onDelete, colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error)) {
                    Text("Eliminar")
                }
            }
        }
    }
}