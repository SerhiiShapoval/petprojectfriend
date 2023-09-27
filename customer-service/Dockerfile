FROM openjdk:17-alpine
WORKDIR /app
COPY target/*.jar customer-service.jar
ENTRYPOINT ["java", "-jar", "customer-service.jar"]