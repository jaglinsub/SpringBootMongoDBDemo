# our base build image
FROM maven:3.5-jdk-8 as maven

# copy the project files
COPY ./pom.xml ./pom.xml

# build all dependencies
RUN mvn dependency:go-offline -B

# copy your other files
COPY ./src ./src

# build for release
RUN mvn package

# our final base image
FROM openjdk:8-jre-alpine

# copy over the built artifact from the maven image
COPY ./target/reactive-web-1.0.0-SNAPSHOT.jar /usr/src/spring-boot-reactive-web/

# set deployment directory
WORKDIR /usr/src/spring-boot-reactive-web

# set the startup command to run your binary
CMD ["java", "-jar", "reactive-web-1.0.0-SNAPSHOT.jar"]