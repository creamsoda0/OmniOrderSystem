# --- 1단계: 빌드 (Builder Stage) ---
# JDK가 포함된 이미지를 기반으로 소스 코드를 빌드합니다.
FROM eclipse-temurin:21-jdk-jammy AS builder
WORKDIR /app

# 1. Gradle Wrapper 관련 파일만 먼저 복사
# gradlew, build.gradle.kts, settings.gradle.kts, 그리고 gradle/wrapper 폴더를 복사합니다.
COPY gradlew .
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY gradle/ gradle/

# 2. apt 업데이트 및 dos2unix 설치
# apt-get을 업데이트하고 dos2unix 유틸리티를 설치하여 줄바꿈 문제를 해결합니다.
RUN apt-get update && apt-get install -y dos2unix

# 3. gradlew 파일의 줄바꿈 문자(CRLF -> LF) 변환
RUN dos2unix gradlew

# 4. gradlew에 실행 권한을 명시적으로 부여 (chmod가 복사 후 바로 적용되도록)
RUN chmod +x ./gradlew

# 5. 나머지 소스 코드 복사
# (build.gradle.kts에 변경이 없을 경우, 이 부분부터 캐시가 적용됩니다.)
COPY . .

# 6. Gradle을 사용하여 JAR 파일을 생성합니다. (테스트는 건너뛰어서 속도를 높입니다)
RUN ./gradlew bootJar -x test

# --- 2단계: 실행 (Runner Stage) ---
# 실제 실행할 가벼운 이미지를 준비합니다.
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app

# 1단계(builder)에서 만든 따끈따끈한 JAR 파일만 가져옵니다.
COPY --from=builder /app/build/libs/*.jar app.jar

# Timezone 설정
ENV TZ=Asia/Seoul

# 실행
ENTRYPOINT ["java", "-jar", "app.jar"]