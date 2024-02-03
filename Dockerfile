FROM openjdk:8-jdk AS build

RUN apt-get update && \
    apt-get install -y curl zip

# Download and install Gradle
RUN curl -s "https://get.sdkman.io" | bash
RUN bash -c "source $HOME/.sdkman/bin/sdkman-init.sh && sdk install gradle"

WORKDIR /app

COPY . .

RUN ./gradlew bootJar --no-daemon

FROM openjdk:8-jdk-slim

EXPOSE 8080

WORKDIR /app

COPY --from=build /app/build/libs/Overflow-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
