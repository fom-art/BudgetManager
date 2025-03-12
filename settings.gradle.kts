pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Budget Manager"
include(":app")
include(":core:data")
include(":core:designsystem")
include(":core:model")
include(":core:utils")
include(":feature:authentication")
include(":feature:friends")
include(":feature:goals")
include(":feature:homepage")
include(":feature:settings")
include(":feature:settlements")
include(":feature:transactions")
