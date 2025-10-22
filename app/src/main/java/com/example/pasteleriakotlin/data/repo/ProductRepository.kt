package com.example.pasteleriakotlin.data.repo

import com.example.pasteleriakotlin.data.model.Product
import kotlinx.coroutines.flow.Flow

// Contrato para cualquier fuente de productos
interface ProductRepository {
    fun getProducts(): Flow<List<Product>>
    fun getProductById(id: String): Flow<Product?>
}
