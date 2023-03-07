FROM maven:3.8.3-openjdk-11 AS builder
WORKDIR /content-maker/backend
COPY pom.xml .
RUN mvn dependency:resolve
COPY src/ ./src/
RUN mvn clean package

FROM adoptopenjdk/openjdk11:alpine-jre
WORKDIR /content-maker/backend
ARG JAR_FILE=target/backend-0.0.1-SNAPSHOT.jar
COPY --from=builder /content-maker/backend/${JAR_FILE} backend.jar
ENTRYPOINT ["java","-jar","backend.jar"]

EXPOSE 8080