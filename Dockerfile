FROM openjdk:17-jdk
COPY Connect_Post_Service*.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]