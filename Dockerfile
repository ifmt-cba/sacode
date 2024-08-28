FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/sacode-1.0.0.jar sacode-1.0.0.jar
EXPOSE 8080
CMD ["java", "-jar", "sacode-1.0.0.jar"]