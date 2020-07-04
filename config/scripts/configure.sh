#!/bin/bash

FILE=~/configured
if test -f "$FILE"; then
  echo "jboss-server has already been configured"
  exit 0;
fi

sed -i -e "s/<resolve-parameter-values>false/<resolve-parameter-values>true/" $JBOSS_HOME/bin/jboss-cli.xml

# ADD SYSADM USER
/opt/jboss/wildfly/bin/add-user.sh sysadm mypass --silent

# Start Jboss
$JBOSS_HOME/bin/standalone.sh &

# Give Jboss a chance to start up
sleep 10

# Create the environment variables to be used in config
printenv > /opt/jboss/wildfly/bin/env.properties

# LOCAL
/bin/bash $JBOSS_HOME/bin/jboss-cli.sh --connect --file=/opt/jboss/wildfly/config/scripts/enable.elytron.cli
/bin/bash $JBOSS_HOME/bin/jboss-cli.sh --connect --file=/opt/jboss/wildfly/config/scripts/init-db.cli
/bin/bash $JBOSS_HOME/bin/jboss-cli.sh --connect --properties=/opt/jboss/wildfly/bin/env.properties --file=/opt/jboss/wildfly/config/scripts/create-db.cli
/bin/bash $JBOSS_HOME/bin/jboss-cli.sh --connect --command=:shutdown 

# Deploy the Web Archive
ln -s /var/deployments/transportation-service.war $JBOSS_HOME/standalone/deployments/transportation-service.war

# REMOTE
# /bin/bash $JBOSS_HOME/bin/jboss-cli.sh --connect --controller=jboss-server:9990 --user=sysadm --password=mypass --file=/opt/jboss/wildfly/config/scripts/enable.elytron.cli
# /bin/bash $JBOSS_HOME/bin/jboss-cli.sh --connect --controller=jboss-server:9990 --user=sysadm --password=mypass --file=/opt/jboss/wildfly/config/scripts/init-db.cli
# /bin/bash $JBOSS_HOME/bin/jboss-cli.sh --connect --controller=jboss-server:9990 --user=sysadm --password=mypass --file=/opt/jboss/wildfly/config/scripts/create-db.cli
# /bin/bash $JBOSS_HOME/bin/jboss-cli.sh --connect --controller=jboss-server:9990 --user=sysadm --password=mypass --file=/opt/jboss/wildfly/config/scripts/deploy.cli

# cleanup
rm /opt/jboss/wildfly/bin/env.properties
touch ~/configured



