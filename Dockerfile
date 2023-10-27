FROM maven:3.8.1-openjdk-17 AS build-stage
WORKDIR /weblab2
COPY . /weblab2/.
RUN echo "FIRST DB_NAME: $DB_NAME"
RUN echo "FIRST INSTANCE_CONNECTION_NAME: $INSTANCE_CONNECTION_NAME"
RUN mvn -f /weblab2/pom.xml clean package -DskipTests=true

FROM openjdk:17-jdk-slim

WORKDIR /weblab2
COPY --from=build-stage /weblab2/target/*.jar /weblab2/*.jar
RUN echo "SECOND DB_NAME: $DB_NAME"
RUN echo "SECOND INSTANCE_CONNECTION_NAME: $INSTANCE_CONNECTION_NAME"
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/weblab2/*.jar"]