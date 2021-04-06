# Run locally
Pass in `dev` profile with:  
`spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=dev`

with a built JAR:
`java -jar -Dspring.profiles.active=dev backend-0.0.1-SNAPSHOT.jar`

# Deploy to gcloud
`gcloud app deploy`
`gcloud app deploy src/main/appengine/cron.yaml`
