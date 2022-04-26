plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.6.20"
}

buildscript {

    repositories {
        mavenCentral()
        gradlePluginPortal()
    }

    dependencies {
        classpath(kotlin("gradle-plugin", version = "1.6.20"))
    }
}

allprojects {

    apply<JavaPlugin>()
    apply(plugin = "org.jetbrains.kotlin.jvm")

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(8))
        }
    }
}
