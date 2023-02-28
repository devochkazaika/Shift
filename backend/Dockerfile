FROM openjdk:11
WORKDIR /content-maker/backend
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY . .
ENTRYPOINT ["java","-jar","app.jar"]