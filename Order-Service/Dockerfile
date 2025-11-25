# --- 1단계: 빌드 (Builder Stage) ---
# JDK가 포함된 이미지를 기반으로 소스 코드를 빌드합니다.
FROM eclipse-temurin:21-jdk-jammy AS builder
WORKDIR /app

# 1. 필수 유틸리티(dos2unix) 미리 설치 (캐시 활용을 위해 가장 먼저 실행)
RUN apt-get update && apt-get install -y dos2unix

# 2. 프로젝트의 모든 파일을 컨테이너 안으로 복사
# (이 명령어가 실행되면 윈도우의 gradlew 원본이 복사되므로, 변환은 이 다음 단계에서 해야 합니다.)
COPY . .

# 3. gradlew 파일 변환 및 실행 권한 부여
# COPY . . 이후에 실행해야 덮어씌워진 파일을 다시 확실하게 고칠 수 있습니다.
RUN dos2unix gradlew && chmod +x ./gradlew

# 4. Gradle을 사용하여 JAR 파일을 생성합니다. (테스트는 건너뛰어서 속도를 높입니다)
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