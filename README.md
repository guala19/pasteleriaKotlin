# 🍰 Pastelería Mil Sabores - App Android Kotlin

<div align="center">

![Kotlin](https://img.shields.io/badge/Kotlin-2.0.21-7F52FF?logo=kotlin)
![Android](https://img.shields.io/badge/Android-35-3DDC84?logo=android)
![Compose](https://img.shields.io/badge/Jetpack%20Compose-2024.09.00-4285F4?logo=android)
![License](https://img.shields.io/badge/License-MIT-green)

**Aplicación Android moderna para catálogo y carrito de pastelería artesanal**

[Descargar](#instalación) • [Características](#características) • [Arquitectura](#arquitectura) • [Paleta](#paleta-de-colores)

</div>

---

## 📱 Descripción

**Mil Sabores** es una aplicación Android nativa desarrollada en **Kotlin con Jetpack Compose** que brinda a los usuarios una experiencia fluida y deliciosa para explorar productos de pastelería artesanal, buscar con inteligencia, y realizar compras sin fricción.

La app refleja los valores de la marca:
- 🏺 **TRADICIÓN**: Tipografía manuscrita y colores tierra
- 🤗 **CALIDEZ**: Espacios generosos y gradientes suaves
- 📚 **NOSTALGIA**: 50 años de historia y recuerdos
- 🚀 **MODERNIDAD**: Responsive, animaciones suave y búsqueda inteligente

---

## ✨ Características

### 🏠 Home Screen
- ✅ Hero section con propuesta de valor
- ✅ Sección "Nuestras Delicias" con productos destacados
- ✅ Timeline "Nuestra Historia" (1974 - Hoy)
- ✅ Beneficios con glassmorphism
- ✅ CTA final dual: "Ver Catálogo" + "Personalizar"

### 📚 Catálogo
- ✅ **14 productos** en **8 categorías**
  - Tortas Cuadradas (2)
  - Tortas Circulares (2)
  - Postres Individuales (2)
  - Sin Azúcar (2)
  - Sin Gluten (2)
  - Veganos (2)
  - Tortas Especiales (2)

### 🔍 Búsqueda Inteligente
- ✅ Busca en: nombre + descripción + categoría + ingredientes
- ✅ Insensible a mayúsculas y acentos
- ✅ Búsqueda parcial: "choco" → "Cheesecake Chocolate"
- ✅ Debounce 300ms (sin lag)
- ✅ Indicador visual de búsqueda activa
- ✅ Botón "Limpiar búsqueda"

### 📊 Filtros
- ✅ Chips por categoría: [Todos] [Cuadrada] [Circular] etc.
- ✅ Combinables: búsqueda + categoría
- ✅ Animación smooth entrada/salida

### 🎯 Product Cards
- ✅ Imagen de 160x160 con bordes redondeados
- ✅ Categoría como badge superior
- ✅ Nombre en tipografía Pacifico (manuscrita)
- ✅ Descripción truncada 2 líneas
- ✅ Precio destacado
- ✅ Tags visuales: ✓ Sin Gluten, ✓ Vegano
- ✅ Hover animation: zoom + elevation +12dp
- ✅ Botón "Agregar al Carrito" full-width

### 🛒 Carrito
- ✅ Badge contador en header
- ✅ Bottom Sheet con resumen
- ✅ Eliminación individual de productos
- ✅ Cálculo automático de total
- ✅ Formato moneda chilena $
- ✅ Botones: Cerrar + Pagar

### 📱 Responsive
- ✅ Mobile: 1 columna (≤600dp)
- ✅ Tablet: 2 columnas (600-900dp)
- ✅ Desktop: 4 columnas (>900dp)
- ✅ Touch targets mínimo 48dp

---

## 🎨 Paleta de Colores

Diseño emocional basado en psicología del color:

| Color | Código | Uso | Psicología |
|-------|--------|-----|-----------|
| **Crema** | #FFF5E1 | Fondos | Tradición, hogar |
| **Crema Claro** | #FFE8D1 | Cards | Aire, claridad |
| **Chocolate** | #8B4513 | Títulos | Confianza, lujo |
| **Chocolate Oscuro** | #5D4037 | Texto | Legibilidad |
| **Rosa Pastel** | #FFC0CB | CTAs | Acción, diversión |
| **Gris** | #B0BEC5 | Secundario | Neutro |

### Código
```kotlin
// En Theme.kt
MaterialTheme(
    colorScheme = LightColors(
        primary = Chocolate,
        secondary = Rose,
        background = Cream
    ),
    typography = PasteleriaTypography
)
```

---

## 🔤 Tipografía

- **Pacifico**: Manuscrita para títulos (28-36sp)
  - Evoca recetas hechas a mano
  - Cálida y accesible
- **Lato**: Moderna para body (12-16sp)
  - Legible en todos los tamaños
  - Profesional y limpia

### Ejemplo
```kotlin
Text(
    text = "50 años endulzando momentos",
    style = MaterialTheme.typography.headlineMedium // Pacifico 32sp
)
```

---

## 📦 Datos de Productos

### Estructura
```kotlin
data class Product(
    val id: String,
    val name: String,
    val description: String,
    val price: Int,
    val imageUrl: String,
    val available: Boolean = true,
    val category: String,
    val ingredients: List<String>,  // Para búsqueda inteligente
    val tags: List<String>          // Sin Gluten, Vegano, etc.
)
```

### Ejemplo
```kotlin
Product(
    id = "1",
    name = "Torta Cuadrada de Chocolate",
    description = "Deliciosa torta con capas de ganache y avellanas",
    price = 45000,
    category = "Tortas Cuadradas",
    ingredients = listOf("chocolate", "ganache", "avellanas", "mantequilla"),
    tags = emptyList()
)
```

---

## 🏗️ Arquitectura

### MVVM + Composable
```
UI Layer (Composables)
    ↓
ViewModel (StateFlow)
    ↓
Repository (Data Layer)
    ↓
Models (Data Classes)
```

### Capas

**Presentation**
- `HomeScreen.kt` - Pantalla principal
- `CatalogScreen.kt` - Listado con búsqueda
- `ProductDetailScreen.kt` - Detalle individual
- `ProductCard.kt` - Card reutilizable
- `CartSheet.kt` - Carrito flotante

**ViewModel**
- `CatalogViewModel.kt` - Lógica búsqueda + filtros
- `ProductDetailViewModel.kt` - Detalle individual
- `UiState` classes - Estados observables

**Data**
- `ProductRepository` - Interfaz repositorio
- `FakeProductRepository` - Datos locales
- `CartRepository` - Carrito en memoria
- `Product` - Modelo datos

**Navigation**
- `PasteleriaNavGraph.kt` - Rutas y navegación
- `Route` sealed class - Destinos tipados

---

## 🚀 Tecnologías

### Framework & UI
- **Jetpack Compose** 2024.09.00 - UI declarativa
- **Material Design 3** - Design System
- **Jetpack Navigation** - Navegación compose

### Architecture
- **ViewModel** - State management
- **StateFlow** - Reactive data streams
- **Repository Pattern** - Data abstraction

### Kotlin
- **Coroutines** - Async programming
- **Kotlin 2.0.21** - Latest language features
- **Sealed Classes** - Type-safe navigation

### Build
- **Gradle 8.13** - Build system
- **Kotlin DSL** - Modern gradle configs
- **AGP 8.13** - Android Gradle Plugin

---

## 📋 Requisitos

- **Android 6.0+** (minSdk 24)
- **Kotlin 2.0.21**
- **Gradle 8.13**
- **JDK 17**

---

## 🔧 Instalación

### 1. Clonar Repositorio
```bash
git clone https://github.com/guala19/pasteleriaKotlin.git
cd pasteleriaKotlin
```

### 2. Abrir en Android Studio
```bash
# Android Studio Koala o superior recomendado
# File > Open > Seleccionar carpeta
```

### 3. Sincronizar Gradle
```bash
./gradlew build
```

### 4. Ejecutar en Emulador
```bash
./gradlew installDebug
```

O en Android Studio: **Shift + F10** (Run)

---

## 📝 Uso

### Búsqueda
1. Ir a **Catálogo**
2. Escribir en campo búsqueda
3. Filtra automáticamente por:
   - Nombre del producto
   - Descripción
   - Categoría
   - Ingredientes

### Filtrar por Categoría
1. Ir a **Catálogo**
2. Tap en chips: [Todos] [Cuadrada] [Circular]
3. Se combinan con búsqueda

### Agregar al Carrito
1. Tap en card de producto
2. O tap en botón "Agregar" directo
3. Badge contador se actualiza
4. Ver carrito: Tap en 🛒 header

### Ver Carrito
1. Tap en botón 🛒 (header)
2. Bottom Sheet con items
3. Tap "Eliminar" para quitar
4. Tap "Pagar" para finalizar

---

## 🎬 Animaciones

### Timing Standard
- **Entrada/Salida**: 200-300ms
- **Curva**: `ease-out`
- **GPU aceleradas**: Compose automático

### Ejemplos
- ProductCard hover: **scale 1.02** + elevation +12dp
- Búsqueda chip: **slideDown 300ms**
- Transiciones screen: **fade 250ms**

---

## 📊 Estructura de Carpetas

```
pasteleriaKotlin/
├── app/
│   └── src/main/
│       ├── java/com/example/pasteleriakotlin/
│       │   ├── MainActivity.kt
│       │   ├── ui/
│       │   │   ├── screen/
│       │   │   │   ├── HomeScreen.kt
│       │   │   │   ├── CatalogScreen.kt
│       │   │   │   └── ProductDetailScreen.kt
│       │   │   ├── components/
│       │   │   │   ├── ProductCard.kt
│       │   │   │   └── CartSheet.kt
│       │   │   ├── theme/
│       │   │   │   ├── Theme.kt
│       │   │   │   └── Typography.kt
│       │   │   ├── catalog/
│       │   │   │   └── CatalogViewModel.kt
│       │   │   ├── product/
│       │   │   │   └── ProductDetailViewModel.kt
│       │   │   ├── cart/
│       │   │   │   └── CartItemUi.kt
│       │   │   ├── navigation/
│       │   │   │   └── PasteleriaNavGraph.kt
│       │   └── data/
│       │       ├── model/
│       │       │   └── Product.kt
│       │       └── repo/
│       │           ├── ProductRepository.kt
│       │           ├── FakeProductRepository.kt
│       │           └── CartRepository.kt
│       └── res/
│           ├── values/
│           │   ├── colors.xml
│           │   ├── strings.xml
│           │   └── themes.xml
│           ├── font/
│           │   ├── pacifico_regular.xml
│           │   ├── lato_regular.xml
│           │   └── lato_bold.xml
│           └── drawable/
│               └── cake.xml
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

---

## 🔄 Flujo de Datos

```
HomeScreen
    ↓ onGoCatalog()
CatalogScreen
    ├─ CatalogViewModel
    │   ├─ observeProducts() → FakeProductRepository
    │   ├─ observeCart() → CartRepository
    │   ├─ updateSearchQuery()
    │   └─ selectCategory()
    │
    ├─ onProductClick(id) → ProductDetailScreen
    │   ├─ ProductDetailViewModel
    │   ├─ ProductRepository.getProductById(id)
    │   └─ onAddToCart() → CartRepository
    │
    └─ CartSheetContent
        ├─ onRemoveFromCart() → CartRepository.remove()
        └─ onPay() → CatalogViewModel.clearCart()
```

---

## 🧪 Testing

### Tests Incluidos
- `ExampleUnitTest.kt` - Unit tests básicos
- `ExampleInstrumentedTest.kt` - Instrumented tests

### Ejecutar Tests
```bash
# Unit Tests
./gradlew test

# Instrumented Tests (requiere emulador)
./gradlew connectedAndroidTest
```

---

## 🎯 Roadmap

### Fase 1 ✅ (Completa)
- ✅ Home con secciones temáticas
- ✅ Catálogo con búsqueda inteligente
- ✅ 14 productos en 8 categorías
- ✅ Carrito funcional
- ✅ Tema visual Mil Sabores

### Fase 2 🔄 (Próxima)
- [ ] Galerías de imágenes
- [ ] Selector tamaño (S/M/L)
- [ ] Personalizador de tortas
- [ ] Dirección de entrega
- [ ] Métodos de pago

### Fase 3 📅 (Futuro)
- [ ] Backend real (Firebase)
- [ ] Sistema de usuarios
- [ ] Historial de pedidos
- [ ] Reseñas y ratings
- [ ] Push notifications

---

## 🐛 Debugging

### Limpiar Cache
```bash
./gradlew clean
```

### Rebuild Completo
```bash
./gradlew build --refresh-dependencies
```

### Ver Errores de Lint
```bash
./gradlew lint
```

### Conectar Emulador
```bash
adb devices
adb -s emulator-5554 logcat
```

---

## 📄 Licencia

MIT License - Ver [LICENSE](LICENSE) para detalles

---

## 👨‍💻 Autor

**Desarrollado por**: GitHub Copilot Expert
**Fecha**: 9 de Noviembre, 2025
**Versión**: 1.0.0

---

## 📧 Soporte

### Reportar Bugs
- Crear Issue en GitHub
- Incluir: Android version, emulator/device, pasos reproducción

### Cambios Solicitados
- Editar `colors.xml` para paleta
- Editar `Typography.kt` para fuentes
- Actualizar `FakeProductRepository.kt` para productos
- Personalizar `HomeScreen.kt` para hero

---

## 🙏 Agradecimientos

Diseño inspirado en principios de:
- **Psicología del color** - Evoca emociones reales
- **UX/UI moderno** - Experiencia frictionless
- **Tradición + Modernidad** - Lo mejor de dos mundos

---

<div align="center">

### ⭐ Si te gusta, marca con ⭐ en GitHub

**[Ver en GitHub](https://github.com/guala19/pasteleriaKotlin)**

🍰 Hecho con ❤️ para Mil Sabores 🍰

</div>
