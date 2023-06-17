val springBootVersion = rootProject.ext.get("springBootVersion")

plugins {
    id("java")
}

repositories {
    mavenCentral()
}
dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux:$springBootVersion")
    {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
    }
//    implementation("org.springframework.boot:spring-boot-starter-log4j2:$springBootVersion")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    enabled = true
}

tasks.bootJar {
    enabled = false
}
