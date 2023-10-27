FROM maven:3.8.1-openjdk-17 AS build-stage
WORKDIR /weblab2
COPY . /weblab2/.
RUN mvn -f /weblab2/pom.xml clean package -DskipTests=true

FROM openjdk:17-jdk-slim

WORKDIR /weblab2
COPY --from=build-stage /weblab2/target/*.jar /weblab2/*.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/weblab2/*.jar"]