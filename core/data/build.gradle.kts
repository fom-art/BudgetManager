plugins {
    alias(libs.plugins.bm.android.library)
}

android {
    namespace = "com.wf.bm.core.data"
}

dependencies {
    api(project(":core:model"))

    implementation ("androidx.datastore:datastore-preferences:1.1.1")

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.android)

    //Koin
    api(libs.koin.core)
    implementation(libs.koin.android)

    //Test
    androidTestImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    androidTestImplementation("org.mockito:mockito-core:5.0.0") // For mocking
    androidTestImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0") // For Kotlin extensions

    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.1") // For coroutine testing
}