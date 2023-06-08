val kotlinVersion = rootProject.ext.get("kotlinVersion")
val springBootVersion = rootProject.ext.get("springBootVersion")

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-quartz:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-webflux:$springBootVersion")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions:1.2.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.7.1")
    implementation(project(":common"))
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("org.postgresql:r2dbc-postgresql")
    testImplementation("io.projectreactor:reactor-test:3.5.6")
    testImplementation("com.squareup.okhttp3:okhttp:4.11.0")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.11.0")
    testImplementation("com.h2database:h2:2.1.214")
    testImplementation("io.r2dbc:r2dbc-h2:1.0.0.RELEASE")
}

tasks.compileKotlin {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    enabled = false
}

tasks.bootJar {
    enabled = true
}
