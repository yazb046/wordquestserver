FROM gradle:7.6.0-jdk17 AS build
WORKDIR /app
COPY --chown=gradle:gradle . /app

#run as nonroot user
RUN groupdadd -r myapp && useradd -g myapp myapp
RUN chown -R myapp:myapp /app
USER myapp


RUN gradle clean build --no-daemon

FROM openjdk:17.0.2-slim as builder
WORKDIR /app
COPY --from=build /app/build/libs/server-1.0.0.jar /app/server-1.0.0.jar
RUN java -Djarmode=layertools -jar server-1.0.0.jar extract

FROM openjdk:17.0.2-slim
WORKDIR /app
COPY --from=builder app/dependencies/ ./
COPY --from=builder app/spring-boot-loader/ ./
COPY --from=builder app/snapshot-dependencies/ ./
COPY --from=builder app/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]