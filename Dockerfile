# Pulling Maven image to build
FROM maven:3.5-jdk-8-alpine as build

# Declare WORKDIR
WORKDIR /app

# Copy src, pom.xml, deployment.yml, Dockerfile and Dockerfile.test into WORKDIR
COPY src /app/src
COPY pom.xml /app
COPY Dockerfile /app
COPY Dockerfile.test /app
COPY deployment.yml /app

# Build project
RUN mvn clean install

# Pulling Java image to build
FROM openjdk:8

# Declare WORKDIR
WORKDIR /app

# Adding jar into WORKDIR
COPY --from=build /app/target/tarfileoperationservice.jar /app/app.jar

# Exposing port 8080
EXPOSE 8080

# Setting entrypoint to run the app
ENTRYPOINT ["java", "-jar", "app.jar"]