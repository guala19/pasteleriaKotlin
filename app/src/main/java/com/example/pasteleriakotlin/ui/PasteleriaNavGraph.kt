package com.example.pasteleriakotlin.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pasteleriakotlin.ui.screen.CatalogScreen
import com.example.pasteleriakotlin.ui.screen.HomeScreen
import com.example.pasteleriakotlin.ui.screen.ProductDetailScreen

/**
 * Rutas de navegación de la app.
 * Se usa una sealed class para evitar strings sueltos.
 */
sealed class Route(val route: String) {
    data object Home : Route("home")
    data object Catalog : Route("catalog")
    data object ProductDetail : Route("product/{id}") {
        fun create(id: String) = "product/$id"
    }
}

/**
 * Gráfico de navegación principal de la pastelería.
 * Define qué pantalla se muestra para cada ruta.
 */
@Composable
fun PasteleriaNavGraph() {
    val nav = rememberNavController()
    NavHost(nav, startDestination = Route.Home.route) {
        composable(Route.Home.route) {
            HomeScreen(onGoCatalog = { nav.navigate(Route.Catalog.route) })
        }
        composable(Route.Catalog.route) {
            CatalogScreen(
                onBack = { nav.popBackStack() },
                onProductClick = { id -> nav.navigate(Route.ProductDetail.create(id)) }
            )
        }
        composable(
            route = Route.ProductDetail.route,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            requireNotNull(id) { "Product ID is required as an argument" }
            ProductDetailScreen(id = id, onBack = { nav.popBackStack() })
        }
    }
}
