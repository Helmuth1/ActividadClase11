package data.repository

import data.model.Product
import data.model.ProductRequest
import data.network.RetrofitInstance
import data.network.ProductApiService

class ProductRepository {

    private val api = RetrofitInstance.api

    // 🔹 Obtener lista completa de productos
    suspend fun listAll(): List<Product> = api.getAllProducts()

    // 🔹 Obtener un producto por ID
    suspend fun get(id: Long): Product = api.getProductById(id)

    // 🔹 Crear un nuevo producto
    suspend fun create(request: ProductRequest): Product = api.createProduct(request)

    // 🔹 Actualizar un producto existente
    suspend fun update(id: Long, request: ProductRequest): Product = api.updateProduct(id, request)

    // 🔹 Eliminar un producto por ID
    suspend fun delete(id: Long) = api.deleteProduct(id)

    // 🔹 Buscar productos por nombre
    suspend fun search(name: String): List<Product> = api.searchProducts(name)
}

