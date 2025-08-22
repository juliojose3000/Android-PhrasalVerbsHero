// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false

    //Firebase Crashlytics
    id("com.google.gms.google-services") version "4.4.2" apply false
    id("com.google.firebase.crashlytics") version "3.0.3" apply false
    alias(libs.plugins.android.library) apply false

    id("com.google.dagger.hilt.android") version "2.51.1" apply false
    alias(libs.plugins.android.kotlin.multiplatform.library) apply false

}