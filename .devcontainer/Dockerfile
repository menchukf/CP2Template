# Start from the universal image
FROM openjdk:21-jdk-slim

# Install openbox
RUN sleep 100; apt-get update && export DEBIAN_FRONTEND=noninteractive \
    && apt-get -y install --no-install-recommends openbox
    
# Set openbox to start automatically
RUN echo 'openbox-session &' >> ~/.xinitrc
