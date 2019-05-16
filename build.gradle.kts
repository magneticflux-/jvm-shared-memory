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
    testImplementation("org.junit.jupiter:junit-jupiter:5.4.2")
    testImplementation("org.amshove.kluent:kluent:1.49")
    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.3.2")
    testImplementation("org.slf4j:slf4j-nop:1.7.26")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}
