# Pull maven with OpenJDK 8 docker image
FROM maven:3.8.5-openjdk-8

# Add some meta
LABEL edu.bu.metcs673.trackr.image.title="Maven" \
      edu.bu.metcs673.trackr.image.title="Run Java Tests in GitHub PRs." \
      edu.bu.metcs673.trackr.image.authors="jdorancy@bu.edu; tflucker@bu.edu; xbhou@bu.edu; weijiel@bu.edu"

# Create directory in container image for app code
RUN mkdir -p /usr/app/

# Copy app code to container destination directory previously created
COPY . /usr/app/

# Set the working directory
WORKDIR /usr/app/

# Run Java tests
RUN mvn verify