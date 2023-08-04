#!/bin/bash

DOCKER_BUILDER=maven:3-jdk-11
DOCKER_TAG=us-central1-docker.pkg.dev/hydro-dashboard-283320/aes-docker-repo/aes-api

VERSION=$1
if [[ -z $VERSION ]]
then
  echo "Missing Version argument"
  exit 1
fi

VERSION_EXISTS=$(git tag --list | grep $VERSION | wc -l)
if [[ $VERSION_EXISTS -gt 0 ]]
then
  echo "Version $VERSION already exists"
  exit 1
fi

docker run --rm \
    -w /aes \
    -v $HOME/.m2:/root/.m2 \
    -v $PWD:/aes \
    $DOCKER_BUILDER mvn clean package -DskipTests=true

docker build -t aes-api:$VERSION .

docker tag aes-api:$VERSION $DOCKER_TAG:$VERSION

docker push $DOCKER_TAG:$VERSION

git tag $VERSION
git push origin $VERSION
