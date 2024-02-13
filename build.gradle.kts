plugins {
    id("java")
}

group = "ge.levanchitiashvili"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.fasterxml.jackson.core:jackson-core:2.13.3")
    implementation("org.json:json:20211205")
    implementation("javax.ws.rs:javax.ws.rs-api:2.1")
    implementation("org.glassfish.jersey.core:jersey-client:2.34")
    implementation("org.glassfish.jersey.core:jersey-common:2.34")
    implementation("org.glassfish.jersey.core:jersey-server:2.34")
}

tasks.test {
    useJUnitPlatform()
}