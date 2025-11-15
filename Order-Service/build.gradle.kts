plugins {
    id("java")
}

group = "com.omni.order"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Spring Boot 기본
    implementation 'org.springframework.boot:spring-boot-starter-web'

    // JPA (데이터베이스 연동)
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'

    // Oracle Driver (직접 추가해야 할 수도 있음)
    // Spring Boot 3.x (Jakarta EE) 호환되는 버전 확인 후 추가
    runtimeOnly 'com.oracle.database.jdbc:ojdbc8' // 또는 ojdbc11

    // Lombok (선택 사항: Getter/Setter 자동 생성)
    compileOnly 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'

    // 테스트 관련
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
}

tasks.test {
    useJUnitPlatform()
}