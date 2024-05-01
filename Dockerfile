# build
FROM maven:3.6.3-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
#RUN mvn dependency:go-offline
COPY src src
RUN mvn clean package install -DskipTests --no-transfer-progress
LABEL authors="maksimbobrov"

# run
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.war app.war
EXPOSE 8000
ENTRYPOINT ["java", "-jar", "app.war"]