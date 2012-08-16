#!/bin/sh

clear
echo "Creating keys"
# you will be promted to enter the password for the certificate (6 chars. min)
keytool -genkey -keystore appscanner.keystore -alias "appscanner" -validity 99999
