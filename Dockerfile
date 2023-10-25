FROM openjdk:17-jdk
COPY build/libs/mail-service-*.jar /app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=deploy", "-jar", "/app.jar"]