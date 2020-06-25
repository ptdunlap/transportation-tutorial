#!/bin/bash

FILE=~/configured
if test -f "$FILE"; then
  echo "jboss-server has already been configured"
  exit 0;
fi

sleep 10

/bin/bash $JBOSS_HOME/bin/jboss-cli.sh --connect --controller=jboss-server:9990 --user=sysadm --password=mypass --file=/opt/jboss/wildfly/config/scripts/enable.elytron.cli
/bin/bash $JBOSS_HOME/bin/jboss-cli.sh --connect --controller=jboss-server:9990 --user=sysadm --password=mypass --file=/opt/jboss/wildfly/config/scripts/init-db.cli
/bin/bash $JBOSS_HOME/bin/jboss-cli.sh --connect --controller=jboss-server:9990 --user=sysadm --password=mypass --file=/opt/jboss/wildfly/config/scripts/create-db.cli
/bin/bash $JBOSS_HOME/bin/jboss-cli.sh --connect --controller=jboss-server:9990 --user=sysadm --password=mypass --file=/opt/jboss/wildfly/config/scripts/deploy.cli

touch ~/configured



