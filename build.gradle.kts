import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformJvmPlugin
import com.github.jengelman.gradle.plugins.shadow.ShadowPlugin

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.6.20"
    id("com.github.johnrengelman.shadow") version "7.1.2"
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
    apply<KotlinPlatformJvmPlugin>()
    apply<ShadowPlugin>()

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(8))
        }
    }
}
