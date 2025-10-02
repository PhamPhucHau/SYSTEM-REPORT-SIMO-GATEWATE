FROM openjdk:17.0.1-jdk-slim
VOLUME /tmp
ADD target/*.jar app.jar
LABEL authors="19521"
ENV JAVA_OPTS=""
EXPOSE 8081
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar"]
