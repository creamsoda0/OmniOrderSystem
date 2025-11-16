plugins {
    id("java")

    id("org.springframework.boot") version "3.1.12"
    id("io.spring.dependency-management") version "1.1.4"
}

dependencyManagement {
    imports {
        // Spring Boot 3.1.x에 호환되는 Spring Cloud 버전을 사용해야 합니다. (예: 2022.0.x)
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2022.0.4")
    }
}

group = "com.omni.order"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Eureka Server 의존성 추가
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-server")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}