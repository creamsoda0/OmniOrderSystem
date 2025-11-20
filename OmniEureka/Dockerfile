# 1. 베이스 이미지 설정 (다른 서비스와 동일하게 Java 17 사용)
FROM eclipse-temurin:21-jdk-jammy

# 2. 메인 jar 파일을 컨테이너 내부로 복사
# (주의: Eureka 서버의 jar 파일명을 확인해야 합니다. 예: omni-eureka-1.0-SNAPSHOT.jar)
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

# 3. Timezone 설정
ENV TZ=Asia/Seoul

# 4. 애플리케이션 실행 명령어
# Eureka 서버의 실행 명령
ENTRYPOINT ["java", "-jar", "/app.jar"]

# 5. 노출 포트 (Eureka 서버의 기본 포트 8761)
EXPOSE 8761