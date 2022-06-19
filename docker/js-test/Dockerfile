# Pull node v18 docker image
FROM node:18.3-buster

# Add some meta
LABEL edu.bu.metcs673.trackr.image.title="Jest" \
      edu.bu.metcs673.trackr.image.title="Run JS Tests in GitHub PRs." \
      edu.bu.metcs673.trackr.image.authors="jdorancy@bu.edu; tflucker@bu.edu; xbhou@bu.edu; weijiel@bu.edu"

# Create directory in container image for app code
RUN mkdir -p /usr/app/

# Copy app code to container destination directory previously created
COPY . /usr/app/

# Set the working directory
WORKDIR /usr/app/

# Install dependencies in package.json
RUN npm install

# Run code sniff
RUN npm test