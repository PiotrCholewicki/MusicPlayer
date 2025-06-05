plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.musicplayer"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.musicplayer"
        minSdk = 28
        targetSdk = 35
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.benchmark.macro)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    val nav_version = "2.9.0"
    implementation("androidx.navigation:navigation-compose:$nav_version")
    implementation(libs.retrofit)
    implementation(libs.retrofit.moshi)
    implementation("com.squareup.moshi:moshi-kotlin:1.15.0")
    implementation("io.coil-kt:coil-compose:2.2.2")


    //ROOM - to be cleaned
    implementation(libs.androidx.room.runtime.android)
    ksp(libs.androidx.room.compiler)
//    implementation("androidx.core:core-ktx:1.16.0")
//    implementation("androidx.appcompat:appcompat:1.6.0")

//    implementation("androidx.appcompat:appcompat:1.6.0")
//    implementation("com.google.android.material:material:1.13.0")
//    implementation("androidx.constraintlayout:constraintlayout:2.2.1")

//    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.0-alpha02")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.7.0-alpha03")

//    implementation("androidx.room:room-runtime:2.7.1")
//    implementation("com.google.dagger:dagger-compiler:2.51.1")
//    ksp("com.google.dagger:dagger-compiler:2.51.1")

    implementation("androidx.lifecycle:lifecycle-livedata-ktx:1.16.0")
//    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
//    implementation("androidx.activity:activity-ktx:1.8.0")
//    implementation("androidx.fragment:fragment-ktx:1.7.0")
    implementation("androidx.compose.runtime:runtime-livedata:1.4.3")
    //implementation(kotlin("stdlib-jdk8"))
}