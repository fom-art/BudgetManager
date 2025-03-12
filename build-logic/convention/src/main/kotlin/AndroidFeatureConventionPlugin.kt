import com.wf.bm.build_logic.convention.implementation
import com.wf.bm.build_logic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies


class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("bm.android.library")
                apply("kotlinx-serialization")
            }

            dependencies {
                implementation(project(":core:model"))
                implementation(project(":core:designsystem"))
                implementation(project(":core:utils"))
                implementation(project(":core:data"))

                implementation(libs.findLibrary("androidx-lifecycle-runtimeCompose").get())
                implementation(libs.findLibrary("androidx-lifecycle-viewModelCompose").get())
                implementation(libs.findLibrary("androidx-navigation-compose").get())
                implementation(libs.findLibrary("kotlinx-serialization-json").get())

                implementation(libs.findLibrary("koin-androidx-compose").get())
                implementation(libs.findLibrary("koin-core").get())
                implementation(libs.findLibrary("koin-compose").get())
                implementation(libs.findLibrary("koin-compose-viewmodel").get())
            }
        }
    }
}