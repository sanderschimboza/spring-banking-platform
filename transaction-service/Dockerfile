#FROM amazoncorretto:17-alpine-jdk
#WORKDIR /opt/app
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} app.jar
#
#ENTRYPOINT ["java", "-jar", "app.jar"]

FROM maven:3.8.4-openjdk-17-slim AS build
LABEL MAINTAINER="schimbozah@gmail.com"

WORKDIR /tmp
COPY pom.xml .
COPY src ./src

RUN mvn package -DskipTests

# For Java 17,
FROM amazoncorretto:17-alpine-jdk
WORKDIR /opt/app
ARG JAR_FILE=target/*.jar
COPY --from=build /tmp/target/*.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]





