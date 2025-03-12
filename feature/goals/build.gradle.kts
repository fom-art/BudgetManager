plugins {
    alias(libs.plugins.bm.android.feature)
    alias(libs.plugins.bm.android.library.compose)
}

android {
    namespace = "com.wf.bm.feature.goals"
}

dependencies {
    implementation (libs.androidx.material.icons.extended)
}