# 1. 빌드 단계 (Build Stage): Java 소스 코드를 JAR 파일로 컴파일
# OpenJDK 17 이상의 JDK가 포함된 이미지 사용 (Kotlin DSL 사용 시)
FROM eclipse-temurin:17-jdk-focal as builder

# 작업 디렉토리 설정
WORKDIR /app

# Gradle Wrapper 및 설정 파일 복사
COPY gradlew .
COPY gradle/ gradle/
COPY settings.gradle.kts .
COPY build.gradle.kts .

# 빌드에 필요한 모든 소스 파일 복사 (자바 코드, 리소스 등)
COPY src src

# Gradle을 사용하여 빌드 및 테스트 실행
# --no-daemon: 데몬 사용 안함
# clean build: 프로젝트를 클린하고 JAR 파일을 생성
RUN ./gradlew clean build -x test

# 2. 실행 단계 (Run Stage): 최종 JAR 파일만 포함하여 이미지 크기 최소화
# JRE만 포함된 경량 이미지 사용
FROM eclipse-temurin:17-jre-focal

# 빌드 단계에서 생성된 JAR 파일 복사
# build/libs 폴더의 JAR 파일을 app.jar 이름으로 복사
COPY --from=builder /app/build/libs/*.jar app.jar

# Spring Boot 앱이 실행될 때 사용할 환경 변수 정의 (선택 사항)
ENV SPRING_PROFILES_ACTIVE=prod

# 8081 포트 노출
EXPOSE 8081

# 애플리케이션 실행 명령어
ENTRYPOINT ["java", "-jar", "app.jar"]