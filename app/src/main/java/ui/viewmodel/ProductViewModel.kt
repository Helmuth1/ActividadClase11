package ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.model.Product
import data.model.ProductRequest
import data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ui.state.ProductUiState

class ProductViewModel(
    private val repository: ProductRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductUiState())
    val uiState: StateFlow<ProductUiState> = _uiState

    /** 🔹 Cargar todos los productos */
    fun loadAll() {
        viewModelScope.launch {
            _uiState.update { it.copy(loading = true, message = null) }

            runCatching {
                repository.listAll()
            }.onSuccess { products ->
                _uiState.update { it.copy(loading = false, items = products) }
            }.onFailure { e ->
                _uiState.update { it.copy(loading = false, message = e.message) }
            }
        }
    }

    /** 🔹 Buscar productos por nombre */
    fun searchByName(name: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(loading = true, message = null) }

            runCatching {
                repository.search(name)
            }.onSuccess { results ->
                _uiState.update { it.copy(loading = false, items = results) }
            }.onFailure { e ->
                _uiState.update { it.copy(loading = false, message = e.message) }
            }
        }
    }

    /** 🔹 Crear nuevo producto */
    fun create(request: ProductRequest) {
        viewModelScope.launch {
            runCatching {
                repository.create(request)
            }.onSuccess { newProduct ->
                _uiState.update { state ->
                    state.copy(items = state.items + newProduct)
                }
            }.onFailure { e ->
                _uiState.update { it.copy(message = e.message) }
            }
        }
    }

    /** 🔹 Actualizar producto existente */
    fun update(id: Long, request: ProductRequest) {
        viewModelScope.launch {
            runCatching {
                repository.update(id, request)
            }.onSuccess { updated ->
                _uiState.update { state ->
                    state.copy(items = state.items.map {
                        if (it.id == updated.id) updated else it
                    })
                }
            }.onFailure { e ->
                _uiState.update { it.copy(message = e.message) }
            }
        }
    }

    /** 🔹 Eliminar producto */
    fun delete(id: Long) {
        viewModelScope.launch {
            runCatching {
                repository.delete(id)
            }.onSuccess {
                _uiState.update { state ->
                    state.copy(items = state.items.filterNot { it.id == id })
                }
            }.onFailure { e ->
                _uiState.update { it.copy(message = e.message) }
            }
        }
    }
}
