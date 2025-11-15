plugins {
    id("java") // Java 기본 플러그인

    // ⚠️ Spring Boot 프로젝트임을 알리는 필수 플러그인 2개 추가
    id("org.springframework.boot") version "3.3.0" // 사용 중인 Spring Boot 버전으로 지정
    id("io.spring.dependency-management") version "1.1.5" // 의존성 관리 플러그인

    // Kotlin DSL 사용 시 추가
    // kotlin("jvm") version "1.9.23"
}

group = "com.omni.order"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

// product-service/build.gradle.kts 파일의 dependencies 블록 수정
dependencies {
    // Spring Boot 기본 (필수)
    implementation("org.springframework.boot:spring-boot-starter-web")

    // JPA (데이터베이스 연동) (필수)
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // Redis (캐싱) (필수)
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    // Oracle Driver (필수)
    runtimeOnly("com.oracle.database.jdbc:ojdbc11")
    // runtimeOnly("com.oracle.database.jdbc:ojdbc8") // 사용하시는 JDK에 따라 선택

    // Lombok (필수)
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // 테스트 관련
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.test {
    useJUnitPlatform()
}