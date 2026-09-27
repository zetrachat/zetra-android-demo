pluginManagement {
    repositories {
        google()
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

rootProject.name = "zetra-android-demo"
include(":app")

// Build the SDK from source when zetra-android is checked out next to this repo.
// Remove this block to use the published artifact instead.
if (file("../zetra-android/settings.gradle.kts").exists()) {
    includeBuild("../zetra-android") {
        dependencySubstitution {
            substitute(module("ai.zetra:zetra-android")).using(project(":zetra"))
        }
    }
}
