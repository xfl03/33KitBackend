FROM gradle:7.4.2-jdk17-alpine
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
COPY . /usr/src/app
RUN gradle build

FROM openjdk:17.0.2
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
COPY --from=0 /usr/src/app/build/libs  /usr/src/app
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "33Kit-0.0.1-SNAPSHOT.jar"]