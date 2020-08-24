includeBuild("laven") // For exclude gradle plugin

pluginManagement {
    repositories {
        mavenCentral()

        maven("https://plugins.gradle.org/m2/")
    }
}
rootProject.name = "laven-sponge"

