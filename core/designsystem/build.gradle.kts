plugins {
    alias(libs.plugins.bm.android.library)
    alias(libs.plugins.bm.android.library.compose)
}

android {
    namespace = "com.wf.bm.core.designsystem"
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:utils"))

    api(libs.androidx.material3.adaptive.navigation.suite)
}