import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.3.31"
    id("org.shipkit.java") version "2.2.5"
}

group = "com.skaggsm"

repositories {
    jcenter()
}

dependencies {
    implementation("net.java.dev.jna:jna:4.4.0")
    implementation("net.java.dev.jna:platform:3.4.0")

    testImplementation(kotlin("stdlib-jdk8"))
    testImplementation("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
