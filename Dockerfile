# Build stage
FROM maven:3-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /app/target/course-api-0.0.1-SNAPSHOT.jar course-api.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","course-api.jar"]
