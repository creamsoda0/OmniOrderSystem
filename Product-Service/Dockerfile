# --- 1단계: 빌드 (Builder Stage) ---
# JDK가 포함된 이미지를 기반으로 소스 코드를 빌드합니다.
FROM eclipse-temurin:21-jdk-jammy AS builder
WORKDIR /app

# 프로젝트의 모든 파일을 컨테이너 안으로 복사합니다.
COPY . .

# 윈도우에서 실행 권한 문제가 생길 수 있으므로 실행 권한을 부여합니다.
RUN chmod +x ./gradlew

# RUN ./gradlew bootJar -x test

# 다음 명령으로 대체합니다.
# bash 쉘을 사용하고, ./gradlew의 전체 경로를 사용합니다.
RUN /bin/bash -c "./gradlew bootJar -x test"

# --- 2단계: 실행 (Runner Stage) ---
# 실제 실행할 가벼운 이미지를 준비합니다.
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app

# 1단계(builder)에서 만든 따끈따끈한 JAR 파일만 가져옵니다.
# (build/libs/ 폴더 안에 있는 .jar 파일을 app.jar로 복사)
COPY --from=builder /app/build/libs/*.jar app.jar

# Timezone 설정
ENV TZ=Asia/Seoul

# 실행
ENTRYPOINT ["java", "-jar", "app.jar"]