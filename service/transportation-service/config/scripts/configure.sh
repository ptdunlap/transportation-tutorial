#!/bin/bash

FILE=~/configured
if test -f "$FILE"; then
  echo "jboss-server has already been configured"
  exit 0;
fi

# Enable variable resolution in Jboss CLI
sed -i -e "s/<resolve-parameter-values>false/<resolve-parameter-values>true/" $JBOSS_HOME/bin/jboss-cli.xml

# Create the environment variables to be used in config
printenv > /opt/jboss/wildfly/bin/env.properties

# Configure the postgresql data source
/bin/bash $JBOSS_HOME/bin/jboss-cli.sh --properties=/opt/jboss/wildfly/bin/env.properties --file=/opt/jboss/wildfly/config/scripts/configure-datasource.cli

# Deploy the Web Archive
ln -s /var/deployments/transportation-service.war $JBOSS_HOME/standalone/deployments/transportation-service.war

# cleanup
rm /opt/jboss/wildfly/bin/env.properties
touch ~/configured



