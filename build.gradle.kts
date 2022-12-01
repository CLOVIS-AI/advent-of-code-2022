import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("com.google.devtools.ksp") version "1.6.10-1.0.4"
}

group = "dev.canet"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.arrow-kt:arrow-core:_")
    implementation("io.arrow-kt:arrow-optics:_")

    ksp("io.arrow-kt:arrow-optics-ksp-plugin:_")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
    kotlinOptions.freeCompilerArgs += "-Xcontext-receivers"
}