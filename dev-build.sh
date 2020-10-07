#!/bin/bash
mvn clean && mvn install
find . -name 'NoMobGriefing*.jar' -exec cp -- {} "../../Servers/1.16 server/plugins" \;
