import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.4.5"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.4.32"
    kotlin("plugin.spring") version "1.4.32"
    groovy
}

group = "com.icosahedron.demo"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
//    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.github.javafaker:javafaker:1.0.2")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
//    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("io.r2dbc:r2dbc-h2")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.jetbrains:markdown:0.2.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.springframework.boot:spring-boot-starter-rsocket")

    runtimeOnly("com.h2database:h2")

    testImplementation("org.codehaus.groovy:groovy:3.0.5")
    testImplementation("org.spockframework:spock-core:2.0-M3-groovy-3.0")
    testImplementation("org.spockframework:spock-spring:2.0-M3-groovy-3.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude("org.junit.vintage', module: 'junit-vintage-engine")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}
