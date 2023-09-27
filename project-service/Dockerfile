FROM openjdk:17-alpine
WORKDIR /app
COPY target/*.jar project-service.jar
ENTRYPOINT ["java", "-jar", "project-service.jar"]