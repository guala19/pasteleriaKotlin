package com.example.pasteleriakotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.pasteleriakotlin.ui.navigation.PasteleriaNavGraph
import com.example.pasteleriakotlin.ui.theme.PasteleriaTheme

// Punto de entrada que enciende Compose y la navegacion
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PasteleriaTheme {
                PasteleriaNavGraph()
            }
        }
    }
}
