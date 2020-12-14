# Run locally
Pass in `dev` profile with:  
`spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=dev`

# Deploy to gcloud
`gcloud app deploy`
`gcloud app deploy src/main/appengine/cron.yaml`
