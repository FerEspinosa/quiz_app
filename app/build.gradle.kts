plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.curso.android.app.practica.quizapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.curso.android.app.practica.quizapp"
        minSdk = 27
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        dataBinding = true
    }

}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation (libs.gson)

    // Retrofit for HTTP requests
    implementation (libs.retrofit)
    implementation (libs.converter.gson)

    // Lifecycle components for MVVM
    implementation (libs.lifecycle.viewmodel)
    implementation (libs.lifecycle.livedata)

    // Gson for JSON parsing
    implementation (libs.gson)

}