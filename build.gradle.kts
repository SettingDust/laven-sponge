import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "1.4.0"

    `maven-publish`

    id("com.github.johnrengelman.shadow") version "6.0.0"
}

val major = 1
val minor = 0
val patch = 2

group = "me.settingdust"
version =
    "${arrayOf(major, minor, patch).joinToString(".")}${
        @OptIn(ExperimentalStdlibApi::class)
        buildList {
            add("")
            if (System.getenv("BUILD_NUMBER") != null) {
                add(System.getenv("BUILD_NUMBER").toString())
            }
            if (System.getenv("GITHUB_REF") == null || System.getenv("GITHUB_REF").endsWith("-dev")) {
                add("unstable")
            }
        }.joinToString("-")
    }"

repositories {
    mavenCentral()
    maven("https://repo.spongepowered.org/maven/")
}

dependencies {
    implementation(kotlin("stdlib-jdk8", "1.4.0"))

    implementation("org.spongepowered:spongeapi:7.3.0")

    api("org.spongepowered:configurate-ext-kotlin:3.7.1")
    val laven = "me.settingdust:laven"
    api(laven)
    shadow(laven) {
        exclude("org.jetbrains.kotlin")
    }
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/SettingDust/laven-sponge")
            credentials {
                username = project.findProperty("gpr.user") as? String ?: System.getenv("GPR_USER")
                password = project.findProperty("gpr.key") as? String ?: System.getenv("GPR_API_KEY")
            }
        }
    }
    publications {
        create<MavenPublication>("gpr") {
            from(components["java"])
        }
    }
}

tasks {
    compileKotlin { kotlinOptions.jvmTarget = "1.8" }
    compileTestKotlin { kotlinOptions.jvmTarget = "1.8" }
    jar { finalizedBy(shadowJar) }
    shadowJar {
        archiveClassifier.set("")
        configurations = listOf(project.configurations.shadow.get())
        exclude("META-INF/**")
    }
}