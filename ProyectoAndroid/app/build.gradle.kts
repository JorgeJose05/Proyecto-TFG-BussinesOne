plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.proyectobussinesone"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.proyectobussinesone"
        minSdk = 27
        targetSdk = 34
        kotlinOptions {
            jvmTarget = "1.8"
        }
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        compose = true
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"  // o la más reciente
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
        // Retrofit
        implementation("com.squareup.retrofit2:retrofit:2.9.0")
        // Converter para JSON (Gson en este ejemplo)
        implementation("com.squareup.retrofit2:converter-gson:2.9.0")


    //Gson
    implementation ("com.google.code.gson:gson:2.8.9")

    //zxingx crear codigos de barras
    implementation ("com.google.zxing:core:3.4.0")

    //camara 2
    implementation ("androidx.camera:camera-camera2:1.3.4")

    //camara
    implementation ("androidx.camera:camera-core:1.3.4")
    implementation ("androidx.camera:camera-camera2:1.3.4")
    implementation ("androidx.camera:camera-lifecycle:1.3.4")
    implementation ("androidx.camera:camera-view:1.3.4")
    //

    implementation ("com.google.mlkit:barcode-scanning:17.2.0")
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    implementation ("androidx.navigation:navigation-fragment:2.8.9")
    implementation ("androidx.navigation:navigation-ui:2.8.9")
    implementation ("androidx.navigation:navigation-compose:2.8.9")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")
    implementation("androidx.compose.material:material-icons-extended:1.5.1")
    implementation ("androidx.compose.material3:material3:1.3.2")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation ("androidx.compose.ui:ui:1.5.1")
    implementation ("androidx.fragment:fragment-compose:1.8.0")
    implementation(libs.androidx.material3.android)
    implementation(libs.androidx.ui.viewbinding)
    implementation(libs.androidx.camera.view)
    implementation(libs.play.services.mlkit.barcode.scanning)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.firebase.crashlytics.buildtools)
    implementation(libs.androidx.foundation.layout.android)
    implementation(libs.androidx.foundation.layout.android)
    implementation(libs.androidx.foundation.layout.android)
    implementation(libs.androidx.foundation.layout.android)
    debugImplementation("androidx.compose.ui:ui-tooling:1.5.1")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.5.1")
    implementation ("androidx.compose.material:material:1.5.1")
    implementation ("androidx.compose.ui:ui-tooling-preview:1.5.1")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}