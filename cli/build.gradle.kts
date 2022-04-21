import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

dependencies {

    implementation(libs.kxml)
    implementation(libs.apache.commons.lang)
    implementation(libs.apache.commons.io)
    implementation(libs.clikt)
}

tasks {
    named<ShadowJar>("shadowJar") {
        manifest {
            attributes["Main-Class"] = "com.codyi.xml2axml.MainKt"
        }
        isZip64 = true
        // Ignores the "-all" classifier shadow uses by default
        archiveBaseName.set(rootProject.name)
        archiveClassifier.set("")
    }
    register("executable") {
        logger.info("Packaging ${project.name} into an executable binary...")
        dependsOn("shadowJar")

        doLast {
            val jarFile = File(
                project.buildDir,
                "libs/${rootProject.name}-${project.version}.jar"
            )
            require(jarFile.exists()) { "shadowJar output file at ${jarFile.canonicalPath} does not exist!" }
            val executableFile = File(project.buildDir, "libs/${rootProject.name}")

            executableFile.apply {
                writeText("#!/usr/bin/env bash\nexec java -jar \$0 \"\$@\"\n")
                appendBytes(jarFile.readBytes())
                setExecutable(true)
            }
        }
    }
}
