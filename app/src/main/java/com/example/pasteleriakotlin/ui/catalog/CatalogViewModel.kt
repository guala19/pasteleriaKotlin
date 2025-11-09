package com.example.pasteleriakotlin.ui.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.pasteleriakotlin.data.model.Product
import com.example.pasteleriakotlin.data.repo.CartRepository
import com.example.pasteleriakotlin.data.repo.ProductRepository
import com.example.pasteleriakotlin.ui.cart.CartItemUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CatalogUiState(
    val isLoading: Boolean = true,
    val products: List<Product> = emptyList(),
    val cartItems: List<CartItemUi> = emptyList(),
    val total: Int = 0,
    val searchQuery: String = "",
    val selectedCategory: String? = null,
    val availableCategories: List<String> = emptyList()
)

// Mantiene la lista de productos y el estado del carrito
class CatalogViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CatalogUiState())
    val uiState: StateFlow<CatalogUiState> = _uiState.asStateFlow()
    
    private var allProducts = listOf<Product>()

    init {
        observeProducts()
        observeCart()
    }

    private fun observeProducts() {
        viewModelScope.launch {
            productRepository.getProducts().collectLatest { products ->
                allProducts = products
                val categories = products.map { it.category }.distinct().sorted()
                _uiState.update { 
                    it.copy(
                        products = filterProducts(products, it.searchQuery, it.selectedCategory),
                        isLoading = false,
                        availableCategories = categories
                    ) 
                }
            }
        }
    }

    private fun observeCart() {
        viewModelScope.launch {
            cartRepository.itemsFlow().collectLatest { items ->
                val mapped = items.map {
                    CartItemUi(
                        id = it.product.id,
                        name = it.product.name,
                        quantity = it.quantity,
                        subtotal = it.product.price * it.quantity
                    )
                }
                val total = mapped.sumOf { it.subtotal }
                _uiState.update { it.copy(cartItems = mapped, total = total) }
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _uiState.update {
            val filtered = filterProducts(allProducts, query, it.selectedCategory)
            it.copy(searchQuery = query, products = filtered)
        }
    }

    fun selectCategory(category: String?) {
        _uiState.update {
            val filtered = filterProducts(allProducts, it.searchQuery, category)
            it.copy(selectedCategory = category, products = filtered)
        }
    }

    private fun filterProducts(
        products: List<Product>,
        searchQuery: String,
        category: String?
    ): List<Product> {
        return products.filter { product ->
            val matchesSearch = if (searchQuery.isEmpty()) {
                true
            } else {
                val query = searchQuery.lowercase().trim()
                product.name.lowercase().contains(query) ||
                product.description.lowercase().contains(query) ||
                product.category.lowercase().contains(query) ||
                product.ingredients.any { it.lowercase().contains(query) }
            }

            val matchesCategory = category == null || product.category == category

            matchesSearch && matchesCategory
        }
    }

    fun addToCart(product: Product) {
        cartRepository.add(product)
    }

    fun removeFromCart(productId: String) {
        cartRepository.remove(productId)
    }

    fun clearCart() {
        cartRepository.clear()
    }

    companion object {
        fun provideFactory(
            productRepository: ProductRepository,
            cartRepository: CartRepository
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return CatalogViewModel(productRepository, cartRepository) as T
                }
            }
        }
    }
}
