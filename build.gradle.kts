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
    implementation("org.openrewrite:rewrite-java:6.0.1")
    runtimeOnly("org.openrewrite:rewrite-java-11:6.0.1")
}
