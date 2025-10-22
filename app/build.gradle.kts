plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.example.pasteleriakotlin"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.pasteleriakotlin"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures { compose = true }
    packaging { resources.excludes += "/META-INF/{AL2.0,LGPL2.1}" }
}

dependencies {
    val composeBom = platform("androidx.compose:compose-bom:2024.10.01")
    implementation(composeBom)

    // Compose + Material 3
    implementation("androidx.activity:activity-compose:1.9.3")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    // Explicit foundation dependency to ensure lazy grid and related
    // classes are packaged and available at runtime.
    implementation("androidx.compose.foundation:foundation")
    // Material icons (for ShoppingCart icon)
    implementation("androidx.compose.material:material-icons-extended")
    debugImplementation("androidx.compose.ui:ui-tooling")

    // Navegación en Compose
    implementation("androidx.navigation:navigation-compose:2.8.3")

    // Coroutines (usadas por el repositorio fake y el carrito)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")
}
