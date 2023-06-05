import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

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
    group = "com.ngblossom"
    repositories {
        mavenCentral()
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
        testImplementation("org.springframework.boot:spring-boot-starter-test:$springBootVersion")
        testImplementation(kotlin("test"))
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        finalizedBy("jacocoTestReport")
    }

    tasks.named<JacocoReport>("jacocoTestReport") {
        dependsOn(tasks.named<Test>("test"))
        group = "verification"
        description = "Generate Jacoco coverage reports after running tests."

        val main by sourceSets
        sourceDirectories.setFrom(files(main.allSource.srcDirs))
        classDirectories.setFrom(files(main.output))
        reports {
            html.required.set(true)
        }
    }
}

tasks.register<JacocoReport>("jacocoRootReport") {
    dependsOn(":scheduler:jacocoTestReport", ":common:jacocoTestReport") // replace with your module names

    val mainSourceSet = files(subprojects.map { "${it.projectDir}/src/main" })
    val mainClassSet = files(subprojects.flatMap { it.the<SourceSetContainer>()["main"].output })

    sourceDirectories.setFrom(mainSourceSet)
    classDirectories.setFrom(mainClassSet)

    executionData.setFrom(fileTree(project.rootDir.absolutePath).include("**/build/jacoco/*.exec"))

    reports {
        html.required.set(true)
    }
}