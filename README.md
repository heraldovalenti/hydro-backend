# Run locally

Pass in `dev` profile with:  
`spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=dev`

With IntelliJ add the program argument `--spring.profiles.active=dev` to the Run Configuration.

With a built JAR:
`java -jar -Dspring.profiles.active=dev backend-0.0.1-SNAPSHOT.jar`

# Deploy to gcloud

`gcloud app deploy`
`gcloud app deploy src/main/appengine/cron.yaml`

# DB backup/restore

backup: `pg_dump -U postgres -d aes -W -F custom -f <date>.sql`  
restore: `psql -U postgres -d aes -W -f <date>.sql`
