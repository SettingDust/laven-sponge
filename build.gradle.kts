plugins {
    val kotlinVersion = "1.4.0-rc"
    kotlin("jvm") version kotlinVersion
    kotlin("kapt") version kotlinVersion

    `maven-publish`

    id("com.github.johnrengelman.shadow") version "6.0.0"
    id("com.diffplug.spotless") version "5.1.0"
}

val major = 1
val minor = 0
val patch = 0

val mainVersion = arrayOf(major, minor, patch).joinToString(".")

group = "me.settingdust"
version = {
    var version = mainVersion
    val suffix = mutableListOf<String>("")
    if (System.getenv("BUILD_NUMBER") != null) {
        suffix += System.getenv("BUILD_NUMBER").toString()
    }
    if (System.getenv("GITHUB_REF") == null || System.getenv("GITHUB_REF").endsWith("-dev")) {
        suffix += "unstable"
    }
    version += suffix.joinToString("-")
    version
}()

repositories {
    maven("https://dl.bintray.com/kotlin/kotlin-eap")
    mavenCentral()
    maven("https://repo.spongepowered.org/maven/")
    maven("https://repo.codemc.org/repository/maven-public")
}

val shadow by configurations.named("shadow")

dependencies {
    api(kotlin("stdlib-jdk8"))

    val sponge = "org.spongepowered:spongeapi:7.2.0"
    kapt(sponge)
    api(sponge)

    val laven = "me.settingdust:laven:latest"
    shadow(laven) {
        exclude("org.jetbrains.kotlin")
    }
    api(laven) {
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
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

spotless {
    val ktlintVersion = "0.37.2"
    kotlin {
        ktlint(ktlintVersion)
    }
    kotlinGradle {
        ktlint(ktlintVersion)
    }
}