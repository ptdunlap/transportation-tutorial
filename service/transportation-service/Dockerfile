# DOCKER FILE used to build the Jboss image

# Specify a base image
FROM jboss/wildfly

# Set the working directory
WORKDIR /opt/jboss/wildfly

# Copy files over
COPY config/postgresql modules/org/postgresql
COPY config/scripts config/scripts
COPY target/transportation-service.war /var/deployments/transportation-service.war

# Jboss will expose 8080 and 9990
EXPOSE 8080 9990

# Default command
CMD ["/bin/bash", "-c", "\"/opt/jboss/wildfly/config/scripts/configure-and-run.sh\""]
