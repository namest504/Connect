plugins {
	java
	id("org.springframework.boot") version "3.0.10"
	id("io.spring.dependency-management") version "1.1.3"
}

group = "xyz.connect.post"
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
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("com.h2database:h2")
	implementation("org.modelmapper:modelmapper:3.1.1")
	implementation("org.springframework.cloud:spring-cloud-starter-aws:2.2.6.RELEASE")
	implementation("org.springframework.boot:spring-boot-starter-data-redis")
//	implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client:4.0.3")

	compileOnly("org.projectlombok:lombok")

	annotationProcessor("org.projectlombok:lombok")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
