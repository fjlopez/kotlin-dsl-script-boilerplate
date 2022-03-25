plugins {
    application
    kotlin("jvm") version "1.6.10"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":script"))

    implementation("org.jetbrains.kotlin:kotlin-scripting-common")
    implementation("org.jetbrains.kotlin:kotlin-scripting-jvm")
    implementation("org.jetbrains.kotlin:kotlin-scripting-jvm-host")

    testImplementation(kotlin("test"))
    testRuntimeOnly("org.jetbrains.kotlin:kotlin-scripting-jvm-host")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("com.example.host.HostKt")
}