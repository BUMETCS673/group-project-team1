# Pull maven with OpenJDK 8 docker image to build the app
FROM maven:3.8.5-openjdk-8

LABEL edu.bu.metcs673.trackr.image.title="Trackr" \
      edu.bu.metcs673.trackr.image.title="Simple application for tracking user transactions" \
      edu.bu.metcs673.trackr.image.authors="jdorancy@bu.edu; tflucker@bu.edu; xbhou@bu.edu; weijiel@bu.edu"

# Create directory in container image for app code
RUN mkdir -p /usr/app/src

# Copy app code to container destination directory previously created
COPY . /usr/app/src

# Set the working directory
WORKDIR /usr/app/src

# Build app Uber JAR with maven
RUN mvn clean install

# Use Java JRE Headless OpenJDK Image to run the app
FROM nimmis/java-centos:openjdk-8-jre-headless

# Create user app
RUN groupadd -r app && useradd -r -gapp app

# Destination folder for project JAR
RUN mkdir -p /usr/app/bin

# Copy app Uber JAR from previous stage and entrypoint script
COPY --from=0 /usr/app/src/target/trackr-1.0-SNAPSHOT.jar /usr/app/bin/trackr.jar
COPY ./docker/entrypoint.sh /usr/app/bin

# Set the working directory
WORKDIR /usr/app/bin

# Ownership to user app and avoid running image as root
RUN chown -R app:app /usr/app/bin

# Make executable
RUN chmod +x entrypoint.sh

# Expose port 8080 in the container image
EXPOSE 8080

# Run app
ENTRYPOINT ["./entrypoint.sh"]