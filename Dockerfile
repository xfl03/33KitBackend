FROM eclipse-temurin:17.0.5_8-jre
COPY build/libs/33Kit-0.0.1-SNAPSHOT.jar /usr/src/app/
WORKDIR /usr/src/app
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "33Kit-0.0.1-SNAPSHOT.jar"]