plugins {
    alias(libs.plugins.android.application)

    // Agregar el complemento de servicios de Google
    id("com.google.gms.google-services")
}

android {
    namespace = "cl.virginiogomez.trabajo022024"
    compileSdk = 35  // Asegúrate de que sea 35

    defaultConfig {
        applicationId = "cl.virginiogomez.trabajo022024"
        minSdk = 24
        targetSdk = 35  // Asegúrate de que sea 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation(libs.play.services.maps)
    implementation(libs.gridlayout)

    // Importar Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:33.4.0"))

    // Agregar las bibliotecas de Firebase que quieras usar, por ejemplo:
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
