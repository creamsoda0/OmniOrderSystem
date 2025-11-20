# 1. 베이스 이미지 설정: 경량화된 Eclipse Temurin (Java 17) 사용 권장
FROM eclipse-temurin:21-jdk-jammy

# 2. 메인 jar 파일을 컨테이너 내부로 복사
# (주의: 프로젝트 빌드 시 생성되는 jar 파일명을 확인해야 합니다. 예: order-service-1.0-SNAPSHOT.jar)
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

# 3. Timezone 설정 (선택 사항, 로그 시간 정확도를 위해 권장)
ENV TZ=Asia/Seoul

# 4. 애플리케이션 실행 명령어
# jar 파일을 실행하며 Spring Profile (예: dev, prod)을 지정할 수 있습니다.
ENTRYPOINT ["java", "-jar", "/app.jar"]

# 5. 노출 포트 (선택 사항)
# Order Service의 기본 포트 8081
EXPOSE 8082