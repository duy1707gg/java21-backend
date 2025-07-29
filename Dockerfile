# Sử dụng base image có Java 21
FROM eclipse-temurin:21-jdk

# Thư mục làm việc trong container
WORKDIR /app

# Copy file JAR đã build từ Maven
COPY target/chamcong-0.0.1-SNAPSHOT.jar app.jar

# Mở cổng ứng dụng (phù hợp với server.port=5000)
EXPOSE 5000

# Lệnh chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]
