import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    kotlin("jvm") version "2.0.0"
    application
}

application {
    mainClass.set("MainKt")
}

group = "com.example"
version = "1.0.0"

dependencies {
    // Kotlin
    implementation(kotlin("stdlib-jdk8"))

    // HTTP
    implementation(platform("org.http4k:http4k-bom:5.20.0.0"))
    implementation("org.http4k:http4k-core")
    implementation("org.http4k:http4k-multipart")
    implementation("org.http4k:http4k-server-undertow")

    // Templating
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.11.0")

    // support libraries
    implementation(platform("dev.forkhandles:forkhandles-bom:2.18.0.1"))
    implementation("dev.forkhandles:time4k")

    // Junit
    testImplementation(platform("org.junit:junit-bom:5.10.2"))
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")

    testImplementation("io.strikt:strikt-core:0.34.1")
}

tasks.test {
    useJUnitPlatform()
}


kotlin {
    jvmToolchain(22)
    compilerOptions {
        freeCompilerArgs.set(listOf("-Xjsr305=strict"))
    }
}

tasks.named<JavaExec>("run").configure {
    jvmArgs("-XX:TieredStopAtLevel=1")
}


tasks.withType<AbstractArchiveTask>().configureEach {
    isPreserveFileTimestamps = false
    isReproducibleFileOrder = true
}
