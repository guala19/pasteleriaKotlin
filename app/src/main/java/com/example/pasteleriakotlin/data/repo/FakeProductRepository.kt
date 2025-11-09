package com.example.pasteleriakotlin.data.repo

import com.example.pasteleriakotlin.data.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

// Lista de 14 productos en 8 categorías para Mil Sabores
class FakeProductRepository : ProductRepository {
    private val items = MutableStateFlow(
        listOf(
            // TORTAS CUADRADAS
            Product(
                id = "1",
                name = "Torta Cuadrada de Chocolate",
                description = "Deliciosa torta cuadrada con capas de ganache y avellanas. Perfecta para compartir.",
                price = 45000,
                imageUrl = "local://cake",
                available = true,
                category = "Tortas Cuadradas",
                ingredients = listOf("chocolate", "ganache", "avellanas", "mantequilla", "huevos"),
                tags = listOf()
            ),
            Product(
                id = "2",
                name = "Torta Cuadrada de Frutilla",
                description = "Biscocho esponjoso con crema y fresas frescas. Toque de mermelada casera.",
                price = 42000,
                imageUrl = "local://cake",
                available = true,
                category = "Tortas Cuadradas",
                ingredients = listOf("harina", "fresas", "crema", "mermelada", "azúcar"),
                tags = listOf()
            ),

            // TORTAS CIRCULARES
            Product(
                id = "3",
                name = "Torta Circular Tres Leches",
                description = "Clásica torta húmeda con mezcla de tres leches. Cobertura de merengue casero.",
                price = 50000,
                imageUrl = "local://cake",
                available = true,
                category = "Tortas Circulares",
                ingredients = listOf("leche evaporada", "leche condensada", "crema", "bizcocho", "merengue"),
                tags = listOf()
            ),
            Product(
                id = "4",
                name = "Torta Circular Selva Negra",
                description = "Torta multicapas con chocolate, cerezas y crema batida. Decoración con virutas.",
                price = 55000,
                imageUrl = "local://cake",
                available = true,
                category = "Tortas Circulares",
                ingredients = listOf("chocolate", "cerezas", "crema", "bizcocho", "ron"),
                tags = listOf()
            ),

            // POSTRES INDIVIDUALES
            Product(
                id = "5",
                name = "Cheesecake de Frutilla",
                description = "Cremoso cheesecake con base de galleta y topping de salsa de frutilla.",
                price = 9990,
                imageUrl = "local://cake",
                available = true,
                category = "Postres Individuales",
                ingredients = listOf("queso cream", "fresas", "galletas", "mantequilla", "huevos"),
                tags = listOf()
            ),
            Product(
                id = "6",
                name = "Brownie de Nueces",
                description = "Intenso brownie de chocolate con nueces tostadas. Consistencia fudgy.",
                price = 3990,
                imageUrl = "local://cake",
                available = true,
                category = "Postres Individuales",
                ingredients = listOf("chocolate", "nueces", "harina", "huevos", "mantequilla"),
                tags = listOf()
            ),

            // PRODUCTOS SIN AZÚCAR
            Product(
                id = "7",
                name = "Torta Sin Azúcar de Chocolate",
                description = "Deliciosa torta apta para diabéticos, endulzada con estevia. Sabor intacto.",
                price = 38000,
                imageUrl = "local://cake",
                available = true,
                category = "Productos Sin Azúcar",
                ingredients = listOf("chocolate", "estevia", "harina", "huevos", "cacao"),
                tags = listOf("Sin Azúcar")
            ),
            Product(
                id = "8",
                name = "Postre Sin Azúcar de Vainilla",
                description = "Esponjoso postre de vainilla sin azúcar refinado. Endulzante natural.",
                price = 8990,
                imageUrl = "local://cake",
                available = true,
                category = "Productos Sin Azúcar",
                ingredients = listOf("vainilla", "eritritol", "harina", "huevos", "leche"),
                tags = listOf("Sin Azúcar")
            ),

            // PRODUCTOS SIN GLUTEN
            Product(
                id = "9",
                name = "Torta Sin Gluten de Almendra",
                description = "Esponjosa torta hecha con harina de almendra. Apta para celíacos.",
                price = 48000,
                imageUrl = "local://cake",
                available = true,
                category = "Productos Sin Gluten",
                ingredients = listOf("almendra molida", "huevos", "miel", "cacao", "almendra"),
                tags = listOf("Sin Gluten")
            ),
            Product(
                id = "10",
                name = "Postre Sin Gluten de Maíz",
                description = "Suave postre hecho con harina de maíz. Libre de gluten garantizado.",
                price = 7990,
                imageUrl = "local://cake",
                available = true,
                category = "Productos Sin Gluten",
                ingredients = listOf("harina de maíz", "huevos", "leche", "vainilla", "azúcar"),
                tags = listOf("Sin Gluten")
            ),

            // PRODUCTOS VEGANOS
            Product(
                id = "11",
                name = "Torta Vegana de Chocolate",
                description = "Torta 100% vegana con chocolate amargo. Sin huevos, leche ni mantequilla.",
                price = 46000,
                imageUrl = "local://cake",
                available = true,
                category = "Productos Veganos",
                ingredients = listOf("chocolate", "leche de almendra", "harina", "cacao", "aceite"),
                tags = listOf("Vegano")
            ),
            Product(
                id = "12",
                name = "Postre Vegano de Coco",
                description = "Delicioso postre vegano con leche y coco. Cremoso y nutritivo.",
                price = 8990,
                imageUrl = "local://cake",
                available = true,
                category = "Productos Veganos",
                ingredients = listOf("coco", "leche de coco", "azúcar", "harina", "aceite"),
                tags = listOf("Vegano")
            ),

            // TORTAS ESPECIALES
            Product(
                id = "13",
                name = "Torta Personalizada Premium",
                description = "Crea tu propia torta con diseño personalizado. Múltiples opciones de sabores.",
                price = 65000,
                imageUrl = "local://cake",
                available = true,
                category = "Tortas Especiales",
                ingredients = listOf("A elegir según preferencia"),
                tags = listOf()
            ),
            Product(
                id = "14",
                name = "Torta de Cumpleaños Tematica",
                description = "Torta temática según tu celebración. Personalizamos decoración y mensaje.",
                price = 58000,
                imageUrl = "local://cake",
                available = true,
                category = "Tortas Especiales",
                ingredients = listOf("A elegir según tema"),
                tags = listOf()
            )
        )
    )

    override fun getProducts(): Flow<List<Product>> = items

    override fun getProductById(id: String): Flow<Product?> =
        items.map { list -> list.find { it.id == id } }
}
