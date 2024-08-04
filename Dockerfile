# 베이스 이미지 설정
FROM openjdk:17-jdk-slim

# 작업 디렉토리 설정
WORKDIR /app

# 프로젝트 파일 복사
COPY . /app

# 빌드 명령어 실행
RUN if [ -f "./gradlew" ]; then \
        echo "Building Gradle (wrapper) project..." && \
        chmod +x ./gradlew && \
        ./gradlew clean assemble -x test --build-cache --parallel; \
    elif [ -f "./build.gradle" ]; then \
        echo "Building Gradle project..." && \
        gradle clean assemble -x test --build-cache --parallel; \
    elif [ -f "./build.gradle.kts" ]; then \
        echo "Building Gradle Kotlin DSL project..." && \
        gradle clean assemble -x test --build-cache --parallel; \
    elif [ -f "./mvnw" ]; then \
        echo "Building Maven project..." && \
        chmod +x ./mvnw && \
        ./mvnw package -Dmaven.test.skip=true -e -ntp; \
    elif [ -f "./pom.xml" ]; then \
        echo "Building Maven project..." && \
        mvn package -Dmaven.test.skip=true -ntp; \
    elif [ -f "./build.xml" ]; then \
        echo "Building Ant project..." && \
        ant build; \
    else \
        echo "Build target file not found" && \
        exit 1; \
    fi

# JAR 파일을 복사하고 실행
COPY build/libs/fit_mate-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
