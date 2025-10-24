package com.example.pasteleriakotlin.ui.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Storefront
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.pasteleriakotlin.R
import com.example.pasteleriakotlin.data.model.Product
import com.example.pasteleriakotlin.ui.catalog.CatalogUiState

private enum class HomeTab(@StringRes val labelRes: Int) {
    Catalog(R.string.nav_catalog),
    Location(R.string.nav_location)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    catalogUiState: CatalogUiState,
    onProductClick: (String) -> Unit,
    onAddToCart: (Product) -> Unit,
    onRemoveFromCart: (String) -> Unit,
    onPay: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by rememberSaveable { mutableStateOf(HomeTab.Catalog) }
    var showCart by rememberSaveable { mutableStateOf(false) }
    val itemCount = catalogUiState.cartItems.sumOf { it.quantity }

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.logo_foreground),
                            contentDescription = null
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(text = stringResource(R.string.app_name))
                    }
                },
                actions = {
                    if (selectedTab == HomeTab.Catalog) {
                        BadgedBox(badge = {
                            if (itemCount > 0) Badge { Text(itemCount.toString()) }
                        }) {
                            IconButton(onClick = { showCart = true }) {
                                Icon(
                                    imageVector = Icons.Filled.ShoppingCart,
                                    contentDescription = stringResource(R.string.nav_catalog)
                                )
                            }
                        }
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                HomeTab.values().forEach { tab ->
                    val selected = tab == selectedTab
                    NavigationBarItem(
                        selected = selected,
                        onClick = { selectedTab = tab },
                        icon = {
                            when (tab) {
                                HomeTab.Catalog -> Icon(
                                    imageVector = Icons.Outlined.Storefront,
                                    contentDescription = null
                                )
                                HomeTab.Location -> Icon(
                                    imageVector = Icons.Outlined.LocationOn,
                                    contentDescription = null
                                )
                            }
                        },
                        label = { Text(stringResource(tab.labelRes)) }
                    )
                }
            }
        }
    ) { innerPadding ->
        when (selectedTab) {
            HomeTab.Catalog -> CatalogContent(
                uiState = catalogUiState,
                modifier = Modifier.padding(innerPadding),
                showCart = showCart,
                onShowCartChange = { showCart = it },
                onProductClick = onProductClick,
                onAddToCart = onAddToCart,
                onRemoveFromCart = onRemoveFromCart,
                onPay = onPay
            )
            HomeTab.Location -> LocationScreen(modifier = Modifier.padding(innerPadding))
        }
    }
}
