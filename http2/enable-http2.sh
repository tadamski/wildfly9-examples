#!/bin/bash

START_DIR=`pwd`

cd $JBOSS_HOME

ALPN_VERSION=8.1.3.v20150130

curl https://raw.githubusercontent.com/undertow-io/undertow/master/core/src/test/resources/server.keystore >standalone/configuration/server.keystore
curl https://raw.githubusercontent.com/undertow-io/undertow/master/core/src/test/resources/server.truststore >standalone/configuration/server.truststore

#Download the ALPN jar we are interested in
curl http://central.maven.org/maven2/org/mortbay/jetty/alpn/alpn-boot/$ALPN_VERSION/alpn-boot-$ALPN_VERSION.jar >bin/alpn-boot-$ALPN_VERSION.jar

#Add ALPN to the boot class path
echo 'JAVA_OPTS="$JAVA_OPTS' " -Xbootclasspath/p:$JBOSS_HOME/bin/alpn-boot-$ALPN_VERSION.jar" '"' >>bin/standalone.conf

./bin/standalone.sh &
sleep 15

#Add a HTTPS connector
./bin/jboss-cli.sh -c "--command=/core-service=management/security-realm=https:add()"
./bin/jboss-cli.sh -c "--command=/core-service=management/security-realm=https/authentication=truststore:add(keystore-path=server.truststore, keystore-password=password, keystore-relative-to=jboss.server.config.dir)"
./bin/jboss-cli.sh -c "--command=/core-service=management/security-realm=https/server-identity=ssl:add(keystore-path=server.keystore, keystore-password=password, keystore-relative-to=jboss.server.config.dir)"
./bin/jboss-cli.sh -c "--command=/subsystem=undertow/server=default-server/https-listener=https:add(socket-binding=https, security-realm=https, enable-http2=true)"

#shut down Wildfly
kill `jps | grep jboss-modules.jar | cut -f1 -d ' ' `
cd $START_DIR

# details of speedy session can be seen on page: chrome://net-internals/#events
