plugins {
    java
    id("org.springframework.boot") version "3.0.11"
    id("io.spring.dependency-management") version "1.1.3"
}

group = "xyz.connect.user"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    //spring security
    implementation("org.springframework.boot:spring-boot-starter-security")
    //spring boot
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    //h2 database
    implementation("com.h2database:h2")
    //model mapper
    implementation("org.modelmapper:modelmapper:3.1.1")
    // MySQL Database
    implementation("mysql:mysql-connector-java:8.0.26")
    //jwt
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
    implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")
    implementation("commons-io:commons-io:2.6")
    //eureka-client
    //  implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client:4.0.3")
    //lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    //spring test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
