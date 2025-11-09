package com.example.pasteleriakotlin.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.pasteleriakotlin.R

// Pantalla de bienvenida con acceso al catálogo y secciones temáticas
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
                            contentDescription = null,
                            modifier = Modifier
                                .width(40.dp)
                                .height(40.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text("Mil Sabores", style = MaterialTheme.typography.headlineSmall)
                    }
                }
            )
        }
    ) { pad ->
        Column(
            modifier = Modifier
                .padding(pad)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // HERO SECTION
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)),
                color = Color.Transparent
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFFFFF5E1),
                                    Color(0xFFFFE8D1)
                                )
                            )
                        )
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.cake),
                        contentDescription = "Torta artesanal",
                        modifier = Modifier
                            .width(120.dp)
                            .height(120.dp)
                    )
                    Spacer(Modifier.height(20.dp))
                    Text(
                        "50 años endulzando momentos inolvidables",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Pastelería artesanal con recetas tradicionales y toques modernos",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0xFF5D4037),
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(24.dp))
                    Button(
                        onClick = onGoCatalog,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Text("Explorar Catálogo", style = MaterialTheme.typography.titleMedium)
                    }
                }
            }

            Spacer(Modifier.height(32.dp))

            // SECCIÓN: NUESTRAS DELICIAS
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                Text(
                    "Nuestras Delicias",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    "Descubre una selección de nuestros productos más populares",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFFB0BEC5)
                )
            }

            Spacer(Modifier.height(16.dp))

            // Grid de 2 destacados (mobile-friendly)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                HighlightCard(
                    icon = "🍫",
                    title = "Torta Chocolate",
                    description = "Con ganache y avellanas",
                    modifier = Modifier.weight(1f)
                )
                HighlightCard(
                    icon = "🍓",
                    title = "Frutilla Premium",
                    description = "Con fresas frescas",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(32.dp))

            // SECCIÓN: NUESTRA HISTORIA
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                Text(
                    "Nuestra Historia",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.height(12.dp))
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFFFE8D1))
                        .padding(16.dp)
                ) {
                    Column {
                        Text(
                            "Desde 1974",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            "Lo que comenzó como un pequeño sueño en una cocina familiar, hoy es una tradición querida. Cada producto es hecho con amor y los mejores ingredientes.",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            Spacer(Modifier.height(32.dp))

            // SECCIÓN: BENEFICIOS
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                Text(
                    "Por qué elegir Mil Sabores",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.height(16.dp))

                BenefitChip("✓ Ingredientes Premium", "Seleccionados con cuidado")
                Spacer(Modifier.height(10.dp))
                BenefitChip("✓ Entrega Rápida", "Hasta tu puerta en 24 horas")
                Spacer(Modifier.height(10.dp))
                BenefitChip("✓ Personalización", "Tu torta, tu diseño")
                Spacer(Modifier.height(10.dp))
                BenefitChip("✓ Opciones Especiales", "Sin gluten, veganas, sin azúcar")
            }

            Spacer(Modifier.height(32.dp))

            // CTA FINAL
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFFFFF5E1), Color(0xFFFFE8D1))
                        )
                    )
                    .padding(24.dp),
                color = Color.Transparent
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "¿Listo para disfrutar?",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Contáctanos para pedidos personalizados",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFFB0BEC5),
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(20.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Button(
                            onClick = onGoCatalog,
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp)
                        ) {
                            Text("Ver Catálogo")
                        }
                        OutlinedButton(
                            onClick = { /* TODO: Personalizar torta */ },
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp)
                        ) {
                            Text("Personalizar")
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
fun HighlightCard(
    icon: String,
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp)),
        color = Color(0xFFFFE8D1)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(icon, style = MaterialTheme.typography.headlineLarge)
            Spacer(Modifier.height(8.dp))
            Text(
                title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(4.dp))
            Text(
                description,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFFB0BEC5),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun BenefitChip(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)),
        color = Color(0xFFFFE8D1)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFFB0BEC5)
            )
        }
    }
}
