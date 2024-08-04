# 베이스 이미지 설정
FROM openjdk:17-jdk-slim

# 작업 디렉토리 설정
WORKDIR /app

# Gradle 또는 Maven을 통해 빌드한 JAR 파일을 복사
COPY build/libs/fit_mate-0.0.1-SNAPSHOT.jar app.jar

# JAR 파일 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
