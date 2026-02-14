# Start from the universal image
FROM FROM mcr.microsoft.com/devcontainers/java:1-21-bullseye

# Install openbox
RUN sleep 100; apt-get update && export DEBIAN_FRONTEND=noninteractive \
    && apt-get -y install --no-install-recommends openbox
    
# Set openbox to start automatically
RUN echo 'openbox-session &' >> ~/.xinitrc
