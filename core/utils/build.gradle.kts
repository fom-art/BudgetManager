plugins {
    alias(libs.plugins.build.logic.library)
    alias(libs.plugins.build.logic.library.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.wf.bm.core.utils"
}

dependencies{
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
}