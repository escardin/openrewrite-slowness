import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.20"
    application
}
application {
    mainClass.set("ParseSpringBootKt")
    applicationDefaultJvmArgs=listOf("-Xmx16g")
}
group "ca.thelovelaces.rewrite"
version "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    maven {
        url =uri("https://repo.gradle.org/gradle/libs-releases-local")
    }
    mavenCentral()
}
val compileKotlin: KotlinCompile by tasks

compileKotlin.kotlinOptions.jvmTarget = "11"

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("org.gradle:gradle-tooling-api:6.7.1")
    implementation("org.openrewrite:rewrite-java:6.1.7")
    implementation("org.openrewrite:rewrite-test:6.1.7")
    implementation("io.micrometer:micrometer-registry-prometheus:latest.release")
    runtimeOnly("org.openrewrite:rewrite-java-11:6.1.7")
}
