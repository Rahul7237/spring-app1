FROM openjdk:8-jdk AS build

# Install curl, zip, and unzip
RUN apt-get update && \
    apt-get install -y curl zip unzip

# Install SDKMAN
RUN curl -s "https://get.sdkman.io" | bash
RUN echo "source \"$HOME/.sdkman/bin/sdkman-init.sh\"" >> "$HOME/.bashrc"
SHELL ["/bin/bash", "-c"]

# Install Gradle using SDKMAN
RUN source "$HOME/.sdkman/bin/sdkman-init.sh" && sdk install gradle

WORKDIR /app

COPY . .

# Make sure the Gradle wrapper script is executable
#RUN chmod +x ./gradlew

# Run Gradle build
#RUN ./gradlew bootJar --no-daemon

FROM openjdk:8-jdk-slim

EXPOSE 8080

WORKDIR /app

COPY --from=build /app/build/libs/Overflow-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
