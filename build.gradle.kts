// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.kotlinParcelize) apply false
    // Add the dependency for the Google services Gradle plugin
    alias(libs.plugins.googleServices) apply false
}
true // Needed to make the Suppress annotation work for the plugins block