package com.example.pasteleriakotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.pasteleriakotlin.ui.navigation.PasteleriaNavGraph
import com.example.pasteleriakotlin.ui.theme.PasteleriaTheme

/**
 * Actividad principal de la app.
 * Solo monta el árbol de Compose y muestra el gráfico de navegación.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Material 3 con Dynamic Color
            PasteleriaTheme {
                // Navegación de toda la app
                PasteleriaNavGraph()
            }
        }
    }
}
