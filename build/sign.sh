#!/bin/sh

clear
echo "Signing"
# this command signs the JAR file
jarsigner -keystore appscanner.keystore ipa.jar "appscanner"
echo "DONE. Navigate to http://localhost/IPAfinder/index.html"
