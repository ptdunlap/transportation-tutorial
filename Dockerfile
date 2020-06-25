# DOCKER FILE used to build the Jboss image

# Specify a base image
FROM jboss/wildfly

COPY config/postgresql /opt/jboss/wildfly/modules/org/postgresql
COPY config/scripts /opt/jboss/wildfly/config/scripts
COPY service/transportation-service/target/transportation-service.war /var/deployments/transportation-service.war

RUN /bin/sh -c '/opt/jboss/wildfly/bin/add-user.sh sysadm mypass --silent'

# Default command
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
