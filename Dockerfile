# Stage 1 - Pull node v18 docker image to build the frontend
FROM node:18.3-buster

# Add metadata
LABEL edu.bu.metcs673.trackr.image.title="Trackr" \
      edu.bu.metcs673.trackr.image.title="Simple application for tracking user transactions" \
      edu.bu.metcs673.trackr.image.authors="jdorancy@bu.edu; tflucker@bu.edu; xbhou@bu.edu; weijiel@bu.edu"

# Create directory in container image for app code
RUN mkdir -p /usr/app/src

# Copy app code to container destination directory previously created
COPY . /usr/app/src

# Set the working directory
WORKDIR /usr/app/src

# Install Webpack, Webpack CLI global then all the dependencies in package.json
RUN npm install -g webpack webpack-cli
RUN npm install

# Build the project JS code and bundle them under static/built/bundle.js to be included in the Uber JAR
RUN webpack

# Stage 2 - Pull maven with OpenJDK 8 docker image to build the app
FROM maven:3.8.5-openjdk-8

# Create directory structure app code
RUN mkdir -p /usr/app/src

# Copy app code with frontend built to this stage
COPY --from=0 /usr/app/src/ /usr/app/src/

# Set the working directory
WORKDIR /usr/app/src

# Build app Uber JAR with maven which will include all resources alread built now. Skip tests there is another pipeline.
RUN mvn clean install -Dmaven.test.skip=true

# Stage 3 - Use Java JRE Headless OpenJDK Image to run the app
FROM nimmis/java-centos:openjdk-8-jre-headless

# Create user app
RUN groupadd -r app && useradd -r -gapp app

# Destination folder for project JAR
RUN mkdir -p /usr/app/bin

# Copy app Uber JAR from previous stage and entrypoint script
COPY --from=1 /usr/app/src/target/trackr-1.0-SNAPSHOT.jar /usr/app/bin/trackr.jar
COPY entrypoint.sh /usr/app/bin

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