plugins {
    id("java") // Java 기본 플러그인

    // ⚠️ Spring Boot 프로젝트임을 알리는 필수 플러그인 2개 추가
    id("org.springframework.boot") version "3.1.12"
    id("io.spring.dependency-management") version "1.1.4"
    // Kotlin DSL 사용 시 추가
    // kotlin("jvm") version "1.9.23"
}

// order-service/build.gradle.kts 파일

dependencyManagement {
    imports {
        // Spring Boot 3.x에 호환되는 Spring Cloud 버전을 사용해야 합니다.
        // 2022.0.x 버전대 사용 예시
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2022.0.4")
    }
}

group = "com.omni.order"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Spring Boot 기본
    implementation("org.springframework.boot:spring-boot-starter-web")

    // Feign Client (MSA 통신을 위한 핵심 의존성)
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign") // BOM에서 버전을 관리할 경우

    // JPA (데이터베이스 연동)
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // Redis (캐싱)
    // Order Service가 아닌 Product Service라면 이 줄을 추가합니다.
    // 현재 Order Service 파일이므로 Redis는 제거하거나 주석 처리하세요.
    // implementation("org.springframework.boot:spring-boot-starter-data-redis")
    runtimeOnly("org.postgresql:postgresql")
    // Oracle Driver
    // Spring Boot 3.x (Jakarta EE) 호환되는 버전으로 하나만 선택합니다.
    runtimeOnly("com.oracle.database.jdbc:ojdbc11")
    // runtimeOnly("com.oracle.database.jdbc:ojdbc8") // 또는 ojdbc8

    // Lombok (선택 사항: Getter/Setter 자동 생성)
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // Eureka 의존성 추가
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")

    // 테스트 관련
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // 1. Flyway 핵심 라이브러리
    implementation ("org.flywaydb:flyway-core")
/*    // 2. PostgreSQL용 Flyway 확장 모듈 (Spring Boot 3.0 이상 필수)
    implementation ("org.flywaydb:flyway-database-postgresql")*/

    // 💡 Swagger (OpenAPI 3) 의존성 추가
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
}

tasks.withType<JavaCompile> { // ✅ Kotlin DSL: '<' 와 '>'를 사용하여 타입을 명시
    options.encoding = "UTF-8"
}