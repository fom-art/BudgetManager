plugins {
    alias(libs.plugins.build.logic.feature)
    alias(libs.plugins.build.logic.library.compose)
}

android {
    namespace = "com.wf.bm.feature.settlements"
}

dependencies {
    implementation (libs.androidx.material.icons.extended)
}