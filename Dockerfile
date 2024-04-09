FROM openjdk:17-jdk

COPY target/*.jar app.jar
ENTRYPOINT ["sh", "-c", "java -Duser.timezone=\"America/Cuiaba\" -jar /app.jar"]