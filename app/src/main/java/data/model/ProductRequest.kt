package data.model

// Representa el cuerpo de la solicitud (POST o PUT) que se envía al backend
data class ProductRequest(
    val name: String,
    val price: Double,
    val category: String?
)
