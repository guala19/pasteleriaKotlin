package com.example.pasteleriakotlin.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pasteleriakotlin.R

/**
 * Pantalla de inicio (Home).
 * Muestra el logo y un botón para ir al catálogo.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onGoCatalog: () -> Unit) {
    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.logo_foreground),
                    contentDescription = null
                )
                Spacer(Modifier.width(8.dp))
                Text("Pastelería")
            }
        })
    }) { pad ->
        Column(Modifier.padding(pad).padding(16.dp)) {
            Text("Bienvenido/a a la Pastelería", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(12.dp))
            Text("Descubre tortas, cheesecakes y más.")
            Spacer(Modifier.height(24.dp))
            Button(onClick = onGoCatalog) { Text("Ver Catálogo") }
        }
    }
}

