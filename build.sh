#!/bin/bash
docker run --rm \
    -w /aes \
    -v $HOME/.m2:/root/.m2 \
    -v $PWD:/aes \
    maven:3-jdk-11 mvn clean package -DskipTests=true

docker build -t aes-api .
