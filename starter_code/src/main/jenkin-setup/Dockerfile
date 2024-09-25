# Use the official Jenkins LTS base image
FROM jenkins/jenkins:lts

# Switch to root user to install Podman inside the Jenkins container
USER root

# Install Podman inside the Jenkins container
RUN apt-get update && apt-get install -y podman

# Create the podman group and add Jenkins user to it
RUN groupadd podman && usermod -aG podman jenkins

# Switch back to Jenkins user
USER jenkins
