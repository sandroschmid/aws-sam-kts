pluginManagement {
    plugins {
        id("org.graalvm.buildtools.native") version "0.9.6"

        val kotlinVersion = "1.8.10"
        kotlin("jvm") version kotlinVersion
        kotlin("plugin.serialization") version kotlinVersion
    }
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "hello-world-native"
