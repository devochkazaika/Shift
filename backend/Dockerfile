FROM gradle:jdk11 AS builder
WORKDIR /content-maker/backend
COPY build.gradle .
COPY src src
RUN gradle clean build

FROM adoptopenjdk/openjdk11:alpine-jre
WORKDIR /content-maker/backend
ARG JAR_FILE=build/libs/backend-0.0.1-SNAPSHOT.jar
COPY --from=builder /content-maker/backend/${JAR_FILE} backend.jar
ENTRYPOINT ["java","-jar","backend.jar"]

EXPOSE 8080