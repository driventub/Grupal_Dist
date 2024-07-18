

plugins {
    id("org.springframework.boot") version "3.3.1"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
    kotlin("plugin.jpa") version "1.9.22"
}

group = "org.example"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    // implementation("org.springframework.cloud:spring-cloud-starter-openfeign:4.1.2")
    // implementation("org.springframework.cloud:spring-cloud-starter-consul-discovery:4.1.2")

    // implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    // implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation("org.postgresql:postgresql")


    // // For reactive programming (similar to Mutiny in Quarkus)
    // implementation("org.springframework.boot:spring-boot-starter-webflux")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

