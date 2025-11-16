plugins {
    id("java")

    // ⚠️ Spring Boot 프로젝트임을 알리는 필수 플러그인 2개 추가
    id("org.springframework.boot") version "3.3.0" // 사용 중인 Spring Boot 버전으로 지정
    id("io.spring.dependency-management") version "1.1.5" // 의존성 관리 플러그인
}

group = "com.omni.order"
version = "1.0-SNAPSHOT"

dependencyManagement {
    imports {
        // Spring Boot 3.x에 호환되는 Spring Cloud 버전을 사용해야 합니다.
        // 2022.0.x 버전대 사용 예시
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2022.0.4")
    }
}


repositories {
    mavenCentral()
}


dependencies {
// Spring Boot 기본
    implementation("org.springframework.boot:spring-boot-starter-web")
// Eureka 의존성 추가
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-server")


    testImplementation(platform("org.junit:junit-sbom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}



tasks.test {
    useJUnitPlatform()
}