plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose) //Starting in Kotlin 2.0, the Compose Compiler Gradle plugin is required

    //Firebase Crashlytics
    id("com.google.firebase.crashlytics")
    id("com.google.gms.google-services")

    //This line allows us to use the Kotlin Compiler Plugin
    id("kotlin-kapt")

    //This line is to use the Hilt Gradle Plugin
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.loaizasoftware.phrasalverbshero"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.loaizasoftware.phrasalverbshero"
        minSdk = 24
        targetSdk = 35
        versionCode = project.findProperty("versionCode")?.toString()?.toInt() ?: 1  //This is set by the CI/CD tool, Bitrise / Github Actions
        versionName = project.findProperty("versionName")?.toString() ?: "1.0"

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

    flavorDimensions += "environment"  // Defines the dimension for flavors

    productFlavors {

        create("local") {
            dimension = "environment"
            //applicationIdSuffix = ".qa"  // App ID will be different for QA
            versionNameSuffix = "-LOCAL"
            buildConfigField ("String", "BASE_URL", "\"http://192.168.0.183:8080/phrasalverbshero/\"")
        }

        create("qa") {
            dimension = "environment"
            //applicationIdSuffix = ".qa"  // App ID will be different for QA
            versionNameSuffix = "-QA"
            buildConfigField ("String", "BASE_URL", "\"https://phrasalverbshero.fly.dev/phrasalverbshero/\"")
        }

        create("prod") {
            dimension = "environment"
            buildConfigField ("String", "BASE_URL", "\"https://phrasalverbshero.fly.dev/phrasalverbshero/\"")
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
        buildConfig = true //it allows us to implement the product flavors in the build.gradle.kts file
        compose = true
    }
}

dependencies {

    implementation(project(":core_ui"))
    implementation(project(":core"))
    implementation(project(":shared"))

    // ----------------------------
    // 🔷 ANDROIDX + COMPOSE CORE
    // ----------------------------
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)

    //Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")

    //Animations
    implementation(libs.androidx.animation)


    // ----------------------------
    // 🌐 NETWORKING
    // ----------------------------

    // Retrofit + Moshi -> https://github.com/square/moshi
    implementation(libs.moshi.v1150)
    implementation(libs.moshi.kotlin)
    implementation(libs.converter.moshi)
    kapt("com.squareup.moshi:moshi-kotlin-codegen:1.15.0") // ✅ Moshi annotation processor
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2") // Show retrofit logs

    //Retrofit + Gson -> https://github.com/square/retrofit | https://github.com/google/gson
    //implementation(libs.retrofit)
    //implementation(libs.converter.gson)

    // RxJava + Retrofit integration -> https://github.com/square/retrofit | https://github.com/ReactiveX/RxJava
    implementation(libs.rxandroid)
    implementation(libs.rxjava)
    implementation(libs.adapter.rxjava2)

    // Chucker (for network debugging) -> https://github.com/ChuckerTeam/chucker
    debugImplementation(libs.chucker.library)
    releaseImplementation(libs.library.no.op)


    // ----------------------------
    // 🔐 DEPENDENCY INJECTION
    // ----------------------------

    //Dagger 2 -> https://github.com/google/dagger
    //implementation(libs.dagger)
    //kapt(libs.dagger.compiler)

    //Hilt
    kapt(libs.hilt.compiler)  // Replace
    implementation(libs.hilt.android) // Replace with the latest version
    implementation(libs.androidx.hilt.navigation.compose) // If you are using ViewModels


    // ----------------------------
    // 📈 LOGGING & ANALYTICS
    // ----------------------------

    //Timber -> https://github.com/JakeWharton/timber
    implementation(libs.timber)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)


    // ----------------------------
    // 🕵️ MEMORY + DEBUGGING TOOLS
    // ----------------------------

    //Leak Canary -> https://square.github.io/leakcanary/getting_started/
    // debugImplementation because LeakCanary should only run in debug builds.
    debugImplementation(libs.leakcanary.android)


    // ----------------------------
    // 🧪 UI Testing (Compose)
    // ----------------------------
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Optional: Compose test helpers mistakenly added to implementation (fix 👇)
    implementation(libs.androidx.ui.test.junit4.android) // ❌ move to androidTestImplementation

    // Navigation testing
    androidTestImplementation(libs.androidx.navigation.testing)


    // ----------------------------
    // ✅ JVM UNIT TESTS (test/)
    // ----------------------------

    // JUnit
    testImplementation(libs.junit)

    // Mockito
    testImplementation(libs.mockito.inline.v520)
    testImplementation(libs.mockito.kotlin.v510)

    // Architecture & Coroutines
    testImplementation(libs.androidx.core.testing)
    testImplementation(libs.kotlinx.coroutines.test.v173)

    // Kotlin assertions
    testImplementation(libs.jetbrains.kotlin.test)

    // Mock HTTP server
    testImplementation(libs.mockwebserver.v4120)


    // ----------------------------
    // ✅ ANDROID INSTRUMENTED TESTS (androidTest/)
    // ----------------------------

    // Espresso + JUnit + Rules
    //androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.rules.v150)

    // Mockito for androidTest
    androidTestImplementation(libs.mockito.android)



}