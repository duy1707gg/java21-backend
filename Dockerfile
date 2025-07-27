# Base image ổn định cho Java 21
FROM eclipse-temurin:21-jdk

# Thư mục làm việc
WORKDIR /app

# Copy file jar
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Mở cổng
EXPOSE 5000

# Lệnh chạy
ENTRYPOINT ["java", "-jar", "app.jar"]
