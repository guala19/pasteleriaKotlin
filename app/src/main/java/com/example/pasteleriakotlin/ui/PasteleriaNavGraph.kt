package com.example.pasteleriakotlin.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pasteleriakotlin.data.repo.CartRepository
import com.example.pasteleriakotlin.data.repo.FakeProductRepository
import com.example.pasteleriakotlin.data.repo.ProductRepository
import com.example.pasteleriakotlin.ui.catalog.CatalogViewModel
import com.example.pasteleriakotlin.ui.product.ProductDetailViewModel
import com.example.pasteleriakotlin.ui.screen.CatalogScreen
import com.example.pasteleriakotlin.ui.screen.HomeScreen
import com.example.pasteleriakotlin.ui.screen.ProductDetailScreen

// Rutas simples para navegar entre pantallas
sealed class Route(val route: String) {
    data object Home : Route("home")
    data object Catalog : Route("catalog")
    data object ProductDetail : Route("product/{id}") {
        fun create(id: String) = "product/$id"
    }
}

// Conecta las pantallas usando Navigation Compose
@Composable
fun PasteleriaNavGraph() {
    val nav = rememberNavController()
    // Repositorios simples para esta demo
    val productRepository: ProductRepository = remember { FakeProductRepository() }
    val cartRepository = remember { CartRepository() }

    // Configuramos la navegación entre pantallas
    NavHost(nav, startDestination = Route.Home.route) {
        composable(Route.Home.route) {
            HomeScreen(onGoCatalog = { nav.navigate(Route.Catalog.route) })
        }
        composable(Route.Catalog.route) {
            val catalogViewModel: CatalogViewModel = viewModel(
                factory = CatalogViewModel.provideFactory(productRepository, cartRepository)
            )
            val uiState by catalogViewModel.uiState.collectAsState()
            CatalogScreen(
                uiState = uiState,
                onBack = { nav.popBackStack() },
                onProductClick = { id -> nav.navigate(Route.ProductDetail.create(id)) },
                onAddToCart = catalogViewModel::addToCart,
                onRemoveFromCart = catalogViewModel::removeFromCart,
                onPay = catalogViewModel::clearCart
            )
        }
        composable(
            route = Route.ProductDetail.route,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("id")
            requireNotNull(productId) { "Product ID is required as an argument" }
            val detailViewModel: ProductDetailViewModel = viewModel(
                factory = ProductDetailViewModel.provideFactory(
                    productId = productId,
                    productRepository = productRepository,
                    cartRepository = cartRepository
                )
            )
            val uiState by detailViewModel.uiState.collectAsState()
            ProductDetailScreen(
                uiState = uiState,
                onBack = { nav.popBackStack() },
                onAddToCart = detailViewModel::addToCart,
                onRemoveFromCart = detailViewModel::removeFromCart,
                onPay = detailViewModel::clearCart
            )
        }
    }
}

