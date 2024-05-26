plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
//    id("com.google.devtools.ksp") version "1.9.24-1.0.20"
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")

}

android {
    namespace = "com.dzul.notestapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.dzul.notestapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    val compose_nav_version = "2.7.7"
    val activity_version = "1.9.0"

    implementation("androidx.navigation:navigation-compose:$compose_nav_version")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
//    implementation(libs.androidx.activity.compose)

    implementation("androidx.activity:activity:$activity_version")
    implementation("androidx.activity:activity-ktx:$activity_version")
    implementation("androidx.activity:activity-compose:$activity_version")


    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation("androidx.compose.material:material:1.6.7")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation("androidx.datastore:datastore-preferences:1.1.1")
    implementation("androidx.datastore:datastore:1.1.1")

    // START ROOM DEPENDENCIES
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    kapt ("androidx.room:room-compiler:$room_version")
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$room_version")
    // optional - Test helpers
    testImplementation("androidx.room:room-testing:$room_version")
    // optional - Paging 3 Integration
    implementation("androidx.room:room-paging:$room_version")
    // END ROOM DEPENDENCIES

    // START HILT DEPENDENCIES
    val dagger_hilt_version = "2.51.1"
    implementation ("com.google.dagger:hilt-android:$dagger_hilt_version")
    kapt ("com.google.dagger:hilt-compiler:$dagger_hilt_version")

    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    // For instrumentation tests
    androidTestImplementation  ("com.google.dagger:hilt-android-testing:$dagger_hilt_version")
    kaptAndroidTest ("com.google.dagger:hilt-compiler:$dagger_hilt_version")

    // For local unit tests
    testImplementation ("com.google.dagger:hilt-android-testing:$dagger_hilt_version")
    kaptTest ("com.google.dagger:hilt-compiler:$dagger_hilt_version")
    // END HILT DEPENDENCIES

    implementation("com.michael-bull.kotlin-result:kotlin-result:2.0.0")

    implementation("io.coil-kt:coil:2.6.0")
    implementation("io.coil-kt:coil-compose:2.6.0")

    var retrofit_ver = "2.11.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofit_ver")
    implementation("com.squareup.retrofit2:converter-gson:$retrofit_ver")
    implementation ("com.squareup.retrofit2:converter-kotlinx-serialization:$retrofit_ver")


    implementation("com.squareup.okhttp3:okhttp:4.12.0")

}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}