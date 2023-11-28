FROM gradle:jdk17-jammy AS builder
WORKDIR /app/mvc-security
COPY src src
COPY settings.gradle .
COPY build.gradle .
RUN gradle clean assemble

FROM amazoncorretto:17-alpine
WORKDIR /app
EXPOSE 80
COPY --from=builder /app/mvc-security/build/libs/spring-mvc-security-0.0.1.jar .
ENTRYPOINT ["java", "-jar", "spring-mvc-security-0.0.1.jar"]
