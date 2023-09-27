FROM openjdk:17-alpine
WORKDIR /app
COPY target/*.jar profile-service.jar
ENTRYPOINT ["java", "-jar", "profile-service.jar"]