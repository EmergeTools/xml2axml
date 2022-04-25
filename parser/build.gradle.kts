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

// TODO: Task for creating jar library
