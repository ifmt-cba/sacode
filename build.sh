#!/bin/bash
#Building application
rm -fr ./target
mvn clean package
mv ./target/*.jar ./target/app.jar

#Configuring to docker build images in minikube registry
minikube docker-env
eval $(minikube -p minikube docker-env)

#Building docker image with application inside
docker build -t br.edu.ifmt/sacode --no-cache .
docker rmi $(docker images -f "dangling=true" -q)
docker volume prune -f