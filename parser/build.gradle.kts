import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

dependencies {
    implementation(libs.aapt2.proto)
    implementation(libs.apache.commons.io)
    implementation(libs.apache.commons.lang)
    implementation(libs.bundletool)
    implementation(libs.kxml)
    implementation(libs.protobuf.kotlin)
    implementation(libs.slf4j.nop)
    implementation(libs.tika)
}

configure<SourceSetContainer> {
    named("main") {
        java.srcDir("src/main/kotlin")
    }
}

tasks {
    named<ShadowJar>("shadowJar") {
        isZip64 = true
        // Ignores the "-all" classifier shadow uses by default
        archiveBaseName.set(project.name)
        archiveClassifier.set("")
    }
}
