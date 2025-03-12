plugins {
    alias(libs.plugins.build.logic.library)
    alias(libs.plugins.kotlin.serialization)
    id("kotlin-parcelize")
}

android {
    namespace = "com.wf.bm.core.model"
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
    //For serialization of LocalDateTime object
    implementation(libs.kotlinx.datetime)

}