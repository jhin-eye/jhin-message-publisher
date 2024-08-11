# 베이스 이미지를 설정 (OpenJDK 17 사용)
FROM openjdk:17-jdk-slim

# dos2unix 설치
RUN apt-get update && apt-get install -y dos2unix

# 작업 디렉토리를 설정 (컨테이너 내부에서 작업할 위치)
WORKDIR /app

# Gradle Wrapper 관련 파일들을 복사 및 실행 권한 부여
COPY gradlew /app/gradlew
COPY gradle /app/gradle/
RUN chmod +x /app/gradlew
RUN dos2unix /app/gradlew

# 전체 소스 코드 복사
COPY . /app

# gradlew 파일이 존재하는지 확인
RUN ls -l /app/gradlew
RUN cat /app/gradlew

# 절대 경로를 사용하여 gradlew 파일 실행
RUN /app/gradlew clean assemble --no-daemon -x test

# 최종 빌드 (테스트는 제외)
RUN /app/gradlew build --no-daemon -x test

# 실행할 JAR 파일을 설정
CMD ["java", "-jar", "/app/build/libs/message-publisher.jar"]
