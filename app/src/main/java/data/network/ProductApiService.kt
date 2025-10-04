package data.network

import data.model.Product
import retrofit2.http.*
import data.model.ProductRequest
import retrofit2.http.*

interface ProductApiService {

    // 🔹 Obtener lista de todos los productos
    @GET("/api/products")
    suspend fun getAllProducts(): List<Product>

    // 🔹 Obtener un producto por su ID
    @GET("/api/products/{id}")
    suspend fun getProductById(@Path("id") id: Long): Product

    // 🔹 Crear un nuevo producto
    @POST("/api/products")
    suspend fun createProduct(@Body request: ProductRequest): Product

    // 🔹 Actualizar un producto existente
    @PUT("/api/products/{id}")
    suspend fun updateProduct(
        @Path("id") id: Long,
        @Body request: ProductRequest
    ): Product

    // 🔹 Eliminar un producto por ID
    @DELETE("/api/products/{id}")
    suspend fun deleteProduct(@Path("id") id: Long)

    // 🔹 Buscar productos por nombre (param: productName)
    @GET("/api/products/search")
    suspend fun searchProducts(@Query("productName") name: String): List<Product>
}

