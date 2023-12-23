buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.0")

        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.49")

        val nav_version = "2.5.0"
        //noinspection GradleDependency
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false
    id("com.google.devtools.ksp") version "1.9.0-1.0.13" apply false
}
val loadingButtonAndroidVersion by extra("2.2.0")
