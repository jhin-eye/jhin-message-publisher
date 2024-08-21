# 베이스 이미지를 설정 (OpenJDK 17 사용)
FROM openjdk:17-jdk-slim

# 환경 변수 설정 (이 값은 컨테이너 내에서 사용 가능)
ENV JAR_NAME=message-publisher

# 작업 디렉토리를 설정 (컨테이너 내부에서 작업할 위치)
WORKDIR /app

# JAR 파일을 복사 (빌드된 JAR 파일을 컨테이너 내부로 복사)
COPY build/libs/message-publisher.jar /app/message-publisher.jar

# JAR 파일을 실행하는 명령어 설정
CMD ["java", "-jar", "/app/message-publisher.jar"]
