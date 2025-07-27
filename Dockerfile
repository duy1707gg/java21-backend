# Base image phù hợp Java 21
FROM eclipse-temurin:21-jdk-alpine

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

EXPOSE 5000
ENTRYPOINT ["java", "-jar", "/app.jar"]
