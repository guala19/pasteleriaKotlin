# 🚀 Guía Rápida para Desarrolladores

## ⚡ Inicio Rápido (5 minutos)

### 1. Clonar y Abrir
```bash
git clone https://github.com/guala19/pasteleriaKotlin.git
# Abrir en Android Studio → File > Open > carpeta proyecto
```

### 2. Esperar Sincronización Gradle
```
📦 Gradle: downloading...
✅ Build successful
```

### 3. Ejecutar
```
Shift + F10 (Run)
o
Run → Run 'app'
```

### 4. 🎉 ¡Listo!
La app carga en emulador con home, catálogo, búsqueda y carrito.

---

## 📚 Estructura Rápida

```
app/src/main/
├── java/...
│   ├── MainActivity.kt (Punto entrada)
│   ├── ui/
│   │   ├── screen/ (Pantallas principales)
│   │   ├── components/ (Componentes reutilizables)
│   │   ├── catalog/ (ViewModel catálogo)
│   │   └── theme/ (Colores y tipografía)
│   └── data/
│       ├── model/ (Product.kt)
│       └── repo/ (Repositorios)
└── res/
    ├── values/ (colors.xml, strings.xml)
    └── drawable/ (Ilustraciones SVG)
```

---

## 🎨 Personalizar Colores

### Archivo: `app/src/main/res/values/colors.xml`

```xml
<!-- Cambiar estos colores -->
<color name="cream">#FFF5E1</color>              <!-- Fondo principal -->
<color name="chocolate">#8B4513</color>         <!-- Títulos, botones -->
<color name="rose">#FFC0CB</color>              <!-- Botones CTA -->
```

Cambios automáticos en toda la app (Theme enlazado).

---

## 🔤 Cambiar Tipografía

### Archivo: `app/src/main/java/.../ui/theme/Typography.kt`

```kotlin
// Usar otra fuente (Google Fonts recomendado)
val PacificoFont = FontFamily(Font(R.font.pacifico_regular))
val LatoFont = FontFamily(Font(R.font.lato_regular))

// O usar fuentes del sistema
val PacificoFont = FontFamily(genericFamily = "serif")
```

---

## ➕ Agregar Productos

### Archivo: `app/src/main/.../data/repo/FakeProductRepository.kt`

```kotlin
class FakeProductRepository : ProductRepository {
    private val items = MutableStateFlow(
        listOf(
            // AGREGAR AQUÍ
            Product(
                id = "15",
                name = "Tu Nuevo Producto",
                description = "Descripción aquí",
                price = 25000,
                category = "Tortas Cuadradas",
                ingredients = listOf("chocolate", "mantequilla"),
                tags = listOf("Sin Gluten") // Opcional
            ),
            // ...resto
        )
    )
}
```

Actualización automática en catálogo ✨

---

## 🔍 Entender la Búsqueda

### Archivo: `CatalogViewModel.kt`

```kotlin
fun filterProducts(products, searchQuery, category) {
    return products.filter { product ->
        // 1. Busca en nombre
        // 2. Busca en descripción
        // 3. Busca en categoría
        // 4. Busca en ingredientes
        
        val matchesSearch = 
            product.name.lowercase().contains(query) ||
            product.description.lowercase().contains(query) ||
            product.category.lowercase().contains(query) ||
            product.ingredients.any { it.contains(query) }
        
        // + Filtra por categoría
        val matchesCategory = category == null || 
                             product.category == category
        
        matchesSearch && matchesCategory
    }
}
```

---

## 🎯 Flujo de Navegación

```
MainActivity.kt
    ↓
PasteleriaNavGraph.kt (Navigation setup)
    ↓
HomeScreen ←→ CatalogScreen ←→ ProductDetailScreen
    ↓
onGoCatalog() (navController.navigate)
```

### Agregar Nueva Pantalla

1. Crear archivo `NewScreen.kt`
2. Definir ruta en `Route` sealed class
3. Agregar `composable()` en NavHost
4. Navegar con `navController.navigate(Route.New.route)`

---

## 🛒 Entender el Carrito

### Archivo: `CartRepository.kt`

```kotlin
class CartRepository {
    data class CartItem(val product: Product, val quantity: Int)
    
    fun add(product: Product) {
        // Si existe: increment quantity
        // Si no existe: agregar quantity = 1
    }
    
    fun remove(productId: String) {
        // Eliminar producto del mapa
    }
    
    fun clear() {
        // Vaciar carrito
    }
}
```

**Observar carrito en cualquier ViewModel:**
```kotlin
cartRepository.itemsFlow().collectLatest { items ->
    // items tiene lista de CartItem actual
    // Se actualiza automáticamente
}
```

---

## 🎬 Agregar Animaciones

### Ejemplo: Fade-in en entrada card

```kotlin
@Composable
fun MyCard() {
    var visible by remember { mutableStateOf(false) }
    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f
    )
    
    LaunchedEffect(Unit) {
        visible = true
    }
    
    Card(modifier = Modifier.alpha(alpha)) {
        // contenido
    }
}
```

**Valores comunes:**
- Entrada/Salida: **200-300ms**
- Scale: **0.95f → 1.0f**
- Alpha: **0f → 1f**
- Elevation: **4dp → 12dp**

---

## 🧪 Debugging

### Ver logs
```bash
# Terminal Android Studio
adb logcat | grep "pasteleriakotlin"
```

### Breakpoints
1. Click en línea número
2. Run → Debug (Shift + F9)
3. Inspect variables en Variables panel

### Recompose Highlighting
Settings → Languages & Frameworks → Kotlin → Compose
✅ Enable Compose Compiler Metrics

---

## 🔧 Build Variances

### Debug (Desarrollo)
```bash
./gradlew assembleDebug
```
- Mayor tamaño
- Símbolos debug
- Optimizaciones desactivadas

### Release (Producción)
```bash
./gradlew assembleRelease
```
- Proguard minification
- Optimizaciones
- Tamaño pequeño

---

## 📝 Commits Útiles

```bash
# Actualizar colores
git commit -m "style: Actualizar paleta a azules"

# Agregar productos
git commit -m "data: Agregar 5 nuevos productos"

# Fijar bug
git commit -m "fix: Búsqueda no funciona sin acentos"

# Nueva feature
git commit -m "feat: Agregar filtro por precio"
```

Sigue **Conventional Commits** ✨

---

## ❌ Problemas Comunes

### "Gradle sync failed"
```bash
./gradlew clean
./gradlew build --refresh-dependencies
# En Android Studio: File > Invalidate Caches
```

### "Product constructor error"
```kotlin
// ❌ MALO: Constructor antiguo
Product("1", "Nombre", "Desc", 1000, "url", true)

// ✅ BIEN: Con campos nuevos
Product(
    id = "1",
    name = "Nombre",
    description = "Desc",
    price = 1000,
    imageUrl = "url",
    category = "Tortas Cuadradas",  // NUEVO
    tags = listOf()
)
```

### "Navigation route not found"
```kotlin
// ✅ Verificar que exista en NavHost
NavHost(navController, startDestination = Route.Home.route) {
    composable(Route.Catalog.route) { ... }  // ← Debe existir
}
```

---

## 🎓 Recursos

- [Jetpack Compose Docs](https://developer.android.com/jetpack/compose)
- [Material Design 3](https://m3.material.io/)
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
- [Android Architecture](https://developer.android.com/topic/architecture)

---

## 📋 Checklist Pre-Commit

Antes de hacer push:

- [ ] Código compila sin errores
- [ ] No hay warnings de Lint
- [ ] Probé en emulador/device
- [ ] Mensaje commit es descriptivo
- [ ] No hay `TODO` u `FIXME` pendientes
- [ ] Indentación consistente (4 espacios)

---

## 🚀 Deploy a Play Store

1. **Generar APK/Bundle**
   ```bash
   ./gradlew bundleRelease
   ```

2. **Firmar con keystore**
   ```bash
   jarsigner -verbose -sigalg SHA1withRSA \
     app/build/outputs/bundle/release/app.aab \
     ~/.android/release-key.keystore
   ```

3. **Upload a Play Store Console**
   - Internal Testing → Closed Testing → Production

---

## 💡 Tips & Tricks

### Recompose rápido
```kotlin
// Hot reload durante desarrollo
Shift + Cmd + R (Mac) o Ctrl + Shift + R (Windows)
```

### Preview en tiempo real
```kotlin
@Preview(widthDp = 320, heightDp = 640)
@Composable
fun ProductCardPreview() {
    ProductCard(
        p = Product(...),
        onClick = {},
        onAddToCart = {}
    )
}
```
Luego: Click en "Preview" arriba de función

### Inspeccionar Layout
```
Layout Inspector:
Tools → Layout Inspector → Select process
→ Visualiza jerarquía Compose en tiempo real
```

---

## 👥 Contribuir

1. Fork el repo
2. Crear branch: `git checkout -b feature/new-feature`
3. Commit cambios: `git commit -m "feat: ..."`
4. Push: `git push origin feature/new-feature`
5. Pull Request

---

<div align="center">

### ¿Preguntas?

📧 Revisar Issues en GitHub  
📚 Ver README.md para más detalles  
🍰 Disfrutar desarrollando para Mil Sabores

**Happy Coding! 🚀**

</div>
