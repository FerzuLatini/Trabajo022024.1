


plugins {
    alias(libs.plugins.android.application) apply false

    // Plugin de servicios de Google

    id("com.google.gms.google-services") version "4.4.2" apply false
}

buildscript {
    dependencies {
        // Dependencia para el plugin de servicios de Google
        classpath("com.google.gms:google-services:4.4.2")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
