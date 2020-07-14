#!/bin/bash

/bin/bash $JBOSS_HOME/config/scripts/configure.sh
/bin/bash $JBOSS_HOME/bin/standalone.sh -b 0.0.0.0

