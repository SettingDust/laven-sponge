includeBuild("laven") // For exclude gradle plugin

pluginManagement {
    repositories {
        maven("https://dl.bintray.com/kotlin/kotlin-eap")

        mavenCentral()

        maven("https://plugins.gradle.org/m2/")
    }
}
rootProject.name = "laven-sponge"

