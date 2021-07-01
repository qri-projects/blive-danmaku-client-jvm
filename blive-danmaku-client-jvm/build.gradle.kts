import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.20"
}

group = "com.ggemo.va"
version = "1.0-SNAPSHOT"

dependencies {
    implementation("org.slf4j:slf4j-api:1.7.31")
    implementation("com.alibaba:fastjson:1.2.62")
    implementation("org.apache.httpcomponents:httpclient:4.5.13")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.1.1")
    testImplementation(kotlin("test"))
    testImplementation("io.insert-koin:koin-test:3.1.1")
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}