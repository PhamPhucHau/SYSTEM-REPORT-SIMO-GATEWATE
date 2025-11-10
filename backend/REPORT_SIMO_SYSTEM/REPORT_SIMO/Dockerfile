FROM openjdk:26-nanoserver-ltsc2022

# Tạo thư mục làm việc mặc định trong container
WORKDIR /app

# Copy file JAR vào thư mục hiện tại (/app)
COPY target/*.jar app.jar

# Mở cổng mà ứng dụng đang dùng
EXPOSE 8082

# Chạy lệnh java -jar từ cmd (Windows style)
ENTRYPOINT ["cmd", "/S", "/C", "java -jar app.jar"]