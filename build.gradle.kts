import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.3.41"
    id("org.shipkit.java") version "2.2.5"
    id("com.github.ben-manes.versions") version "0.22.0"
}

group = "com.skaggsm"

repositories {
    jcenter()
}

dependencies {
    implementation("net.java.dev.jna:jna:4.4.0")
    implementation("net.java.dev.jna:platform:3.4.0")

    testImplementation(kotlin("stdlib-jdk8"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.5.1")
    testImplementation("org.amshove.kluent:kluent:1.53")
    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.4.0")
    testImplementation("org.slf4j:slf4j-nop:2.0.0-alpha0")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
        exceptionFormat = FULL
    }
}

tasks.javadoc {
    options {
        (this as StandardJavadocDocletOptions).tags(
            "apiNote:a:API Note:",
            "implSpec:a:Implementation Requirements:",
            "implNote:a:Implementation Note:"
        )
    }
}
