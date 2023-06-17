val kotlinVersion = "1.8.21"
val springBootVersion = "3.1.0"
rootProject.ext.set("kotlinVersion", kotlinVersion)
rootProject.ext.set("springBootVersion", springBootVersion)

plugins {
    id("org.springframework.boot") version "3.1.0" apply false
    id("io.spring.dependency-management") version "1.1.0" apply false
    id("jacoco")
    kotlin("jvm") version "1.8.21"
    kotlin("plugin.spring") version "1.8.21" apply false
    kotlin("plugin.jpa") version "1.8.21" apply false
}

allprojects {
    apply(plugin = "jacoco")

    jacoco {
        toolVersion = "0.8.10"
    }

    group = "com.ngblossom"
    repositories {
        mavenCentral()
    }

    configurations {
        all {
            exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
        }
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "kotlin-spring")
    apply(plugin = "jacoco")

    java.sourceCompatibility = JavaVersion.VERSION_17

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.2")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.10")
        implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
        implementation("org.springframework.boot:spring-boot-starter-log4j2:$springBootVersion")
        implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.15.2")
        implementation("com.lmax:disruptor:3.4.4")

        testImplementation("org.springframework.boot:spring-boot-starter-test:$springBootVersion")
        testImplementation(kotlin("test"))
    }

    tasks.compileKotlin {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }

    tasks.test {
        useJUnitPlatform()
        finalizedBy("jacocoTestReport")
    }

}

tasks.jacocoTestReport {
    dependsOn(subprojects.map { it.tasks.named("test") })
    sourceDirectories.from(subprojects.map { "${it.projectDir}/src/main/kotlin" })
    classDirectories.from(subprojects.map { it.sourceSets.main.get().output })
    executionData.from(subprojects.map { "${it.buildDir}/jacoco/test.exec" })

    reports {
        html.required.set(true)
    }
}

tasks.test {
    finalizedBy("jacocoTestReport")
}

