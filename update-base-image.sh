#!/bin/bash

DOCKER_BASE_IMAGE=openjdk:11-jdk
DOCKER_TAG=us-central1-docker.pkg.dev/hydro-dashboard-283320/aes-docker-repo/openjdk

VERSION=$1
if [[ -z $VERSION ]]
then
  echo "Missing Version argument (format YYYYMMDD)"
  exit 1
fi

docker pull openjdk:11-jdk
docker tag openjdk:11-jdk $DOCKER_TAG:$VERSION
docker push $DOCKER_TAG:$VERSION
