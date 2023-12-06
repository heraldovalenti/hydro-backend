#!/bin/bash

IMAGE_NAME=aes-api
DOCKER_BUILDER=maven:3-jdk-11
DOCKER_TAG=us-central1-docker.pkg.dev/hydro-dashboard-283320/aes-docker-repo/$IMAGE_NAME
SKIP_APP_BUILD=0

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

for ARG in $@
do
  if [[ $ARG == "--skipAppBuild" ]]; then SKIP_APP_BUILD=1; fi
done

if [[ $SKIP_APP_BUILD == 0 ]]; then
  docker run --rm \
      -w /aes \
      -v $HOME/.m2:/root/.m2 \
      -v $PWD:/aes \
      $DOCKER_BUILDER mvn clean package -DskipTests=true
else
  echo "skipping app build..."
fi


docker build -t $IMAGE_NAME:$VERSION .

docker tag $IMAGE_NAME:$VERSION $DOCKER_TAG:$VERSION

docker push $DOCKER_TAG:$VERSION

git tag $VERSION
git push origin $VERSION
