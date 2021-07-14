#/bin/bash
mvn package -DskipTests=true
java -jar -Dspring.profiles.active=dev target/backend-0.0.1-SNAPSHOT.jar
