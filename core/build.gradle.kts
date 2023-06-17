val kotlinVersion = rootProject.ext.get("kotlinVersion")
val springBootVersion = rootProject.ext.get("springBootVersion")

tasks.register("prepareKotlinBuildScriptModel")

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-webflux:$springBootVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.2")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions:1.2.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.7.1")
    implementation("io.netty:netty-resolver-dns-native-macos:4.1.93.Final:osx-aarch_64")

    implementation(project(":common"))
    implementation(project(":scheduler"))
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("org.postgresql:r2dbc-postgresql")
    testImplementation("io.projectreactor:reactor-test:3.5.6")
}

tasks.jar {
    enabled = false
}

tasks.bootJar {
    enabled = true
}
