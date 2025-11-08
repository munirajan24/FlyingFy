#FROM ubuntu:latest
#LABEL authors="munir"
#
#ENTRYPOINT ["top", "-b"]

#FROM eclipse-temurin:17-jdk-alpine
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]


# Stage 1: build the jar using Maven
FROM maven:3.9.4-eclipse-temurin-17 AS builder
WORKDIR /workspace
COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN mvn -B -f pom.xml dependency:go-offline

COPY src src
RUN mvn -B -DskipTests clean package

# Stage 2: runtime image
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
ARG JAR_FILE=/workspace/target/*.jar
# copy into the working directory /app as app.jar
COPY --from=builder ${JAR_FILE} app.jar
# Run the jar from its actual path (/app/app.jar)
ENTRYPOINT ["java","-jar","/app/app.jar"]
