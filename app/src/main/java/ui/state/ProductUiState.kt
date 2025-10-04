package ui.state

import data.model.Product

data class ProductUiState(
    val loading: Boolean = false,
    val items: List<Product> = emptyList(),
    val message: String? = null
)