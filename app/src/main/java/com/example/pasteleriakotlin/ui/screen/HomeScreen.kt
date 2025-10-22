package com.example.pasteleriakotlin.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pasteleriakotlin.R

// Pantalla de bienvenida con acceso al catalogo
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onGoCatalog: () -> Unit) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.logo_foreground),
                            contentDescription = null
                        )
                        Spacer(Modifier.width(8.dp))
                        Text("Pasteleria")
                    }
                }
            )
        }
    ) { pad ->
        Column(Modifier.padding(pad).padding(16.dp)) {
            Text("Bienvenido/a a la Pasteleria", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(12.dp))
            Text("Descubre tortas, cheesecakes y mas.")
            Spacer(Modifier.height(24.dp))
            Button(onClick = onGoCatalog) { Text("Ver Catalogo") }
        }
    }
}
