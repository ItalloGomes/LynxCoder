#!/bin/bash
echo "verificando se jre está instalado"
if "dpkg --get-selections | grep "openjdk-11-jre-headless"" then
echo "jre já instalado"
else 
echo "instalando pacote jre 11..."
sudo apt install openjdk-11-jre-headless
fi