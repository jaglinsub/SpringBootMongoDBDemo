# our base build image
FROM maven:3.5-jdk-8 AS MAVEN_TOOL_CHAIN

# copy the project files
COPY ./spring-boot-reactive-web/pom.xml ./pom.xml

# build all dependencies
RUN mvn dependency:go-offline -B

# copy your other files
COPY ./spring-boot-reactive-web/src ./src

# build for release
RUN mvn package

# our final base image
FROM openjdk:8-jre-alpine

RUN mkdir /usr/src/
RUN chown -R 777 /usr/src/

# copy over the built artifact from the maven image
COPY --from=MAVEN_TOOL_CHAIN /target/reactive-web-1.0.0-SNAPSHOT.jar /usr/src/spring-boot-reactive-web/

# set deployment directory
WORKDIR /usr/src/spring-boot-reactive-web

# set the startup command to run your binary
CMD ["java", "-jar", "reactive-web-1.0.0-SNAPSHOT.jar"]