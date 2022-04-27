@file:Suppress("UnstableApiUsage")

rootProject.name = "xml2axml"

enableFeaturePreview("VERSION_CATALOGS")

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        google()
    }

    versionCatalogs {
        create("libs") {
            version("protobuf", "3.17.1")

            alias("aapt2-proto").to("com.android.tools.build:aapt2-proto:7.1.3-7984345")
            alias("apache-commons-lang").to("org.apache.commons:commons-lang3:3.12.0")
            alias("apache-commons-io").to("commons-io:commons-io:1.4")
            alias("bundletool").to("com.android.tools.build:bundletool:1.9.1")
            alias("clikt").to("com.github.ajalt.clikt:clikt:3.4.0")
            alias("kxml").to("net.sf.kxml:kxml2:2.3.0")

            // Needed for protobuf XML decoding
            alias("protobuf-kotlin").to("com.google.protobuf", "protobuf-kotlin").versionRef("protobuf")
        }
    }
}

include(":cli")
include(":parser")
