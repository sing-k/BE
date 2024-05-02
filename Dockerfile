FROM gradle:8.7.0-jdk17 as builder
WORKDIR /app

COPY build.gradle settings.gradle gradlew /app/
COPY gradle /app/gradle
COPY src /app/src

RUN ./gradlew build -x test

# 배포
FROM eclipse-temurin:17-jre-jammy as app
WORKDIR /app


COPY --from=builder /app/build/libs/*.jar app.jar
#COPY entrypoint.sh entrypoint.sh
#RUN chmod +x entrypoint.sh

# application-properties와 관련된 파일이 담긴 디렉토리

# 애플리케이션 실행
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
