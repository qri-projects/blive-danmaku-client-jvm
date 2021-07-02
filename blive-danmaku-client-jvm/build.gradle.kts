import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.20"
    `maven-publish`
}

val projectGroup: String = "com.ggemo.va"
val projectVersion: String = "2.0.1-SNAPSHOT"
val projectName: String = "blive-danmaku-client"

group = projectGroup
version = projectVersion

dependencies {
    implementation("org.slf4j:slf4j-api:1.7.31")
    implementation("com.alibaba:fastjson:1.2.62")
    implementation("org.apache.httpcomponents:httpclient:4.5.13")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.1.1")
    testImplementation(kotlin("test"))
    testImplementation("io.insert-koin:koin-test:3.1.1")
    implementation(kotlin("stdlib-jdk8"))
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = projectGroup
            artifactId = projectName
            version = projectVersion

            from(components["java"])
        }
    }
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/qri-projects/blive-danmaku-client-jvm")
            credentials {
                username = "HHHHhgqcdxhg"
                password = System.getenv("GH_TOKEN_ME")
            }
        }
    }
}
repositories {
    mavenCentral()
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}