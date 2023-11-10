FROM openjdk:17-jdk
COPY build/libs/Connect_Post_Service-*-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=common,deploy", "-jar", "/app.jar"]