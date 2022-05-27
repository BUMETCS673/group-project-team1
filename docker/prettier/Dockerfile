# Pull ubuntu docker image
FROM ubuntu

# Add some meta
LABEL edu.bu.metcs673.trackr.image.title="Prettier" \
      edu.bu.metcs673.trackr.image.title="Run code sniffs in GitHub PRs." \
      edu.bu.metcs673.trackr.image.authors="jdorancy@bu.edu; tflucker@bu.edu; xbhou@bu.edu; weijiel@bu.edu"

# Create directory in container image for app code
RUN mkdir -p /usr/app/

# Copy app code to container destination directory previously created
COPY . /usr/app/

# Set the working directory
WORKDIR /usr/app/

# Install NodeJS, NPM
RUN apt-get update
RUN curl -sL https://deb.nodesource.com/setup_14.x | bash -
RUN apt-get install -y nodejs
RUN apt-get install -y npm

# Install dependencies in package.json
RUN npm install

# Run code sniff
RUN npm run sniff