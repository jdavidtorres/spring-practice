FROM openjdk:17 as builder
WORKDIR /app
COPY ./target/msvc-usuarios-0.0.1-SNAPSHOT.jar .
EXPOSE 8001
ENTRYPOINT ["java", "-jar","msvc-usuarios-0.0.1-SNAPSHOT.jar"]
