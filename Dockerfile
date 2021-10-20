FROM openjdk:11-jdk
WORKDIR /aes
COPY target/backend-0.0.1-SNAPSHOT.jar /aes/backend.jar
ENTRYPOINT ["java", "-jar", "backend.jar"]