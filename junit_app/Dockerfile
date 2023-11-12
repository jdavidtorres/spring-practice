FROM eclipse-temurin:11-jdk AS builder
RUN apt-get update && apt-get install -y
RUN apt-get install -y maven
ENV HOME=/opt/src
RUN mkdir -p $HOME
WORKDIR $HOME
ADD . $HOME
RUN mvn clean
