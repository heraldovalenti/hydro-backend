FROM us-central1-docker.pkg.dev/hydro-dashboard-283320/aes-docker-repo/openjdk:20230531
WORKDIR /aes
COPY target/backend-0.0.1-SNAPSHOT.jar /aes/backend.jar
ENTRYPOINT ["java", "-jar", "backend.jar"]