#!/bin/bash

YEL='\033[01;33m'
GRE='\033[01;32m'
RED='\033[01;31m'
WHI='\033[00;37m'

echo "Assistente de instalação v1.0.0"
echo
sleep 3

echo -e "atualizando lista de pacotes e programas instalados... ${YEL}[AGUARDE]${WHI}"
sleep 3

sudo apt update && sudo apt upgrade

echo -e "lista de pacotes atualizados ${GRE}[OK]${WHI}\n"
sleep 3

arr_package=('default-jre')

for((cont=0;cont<${#arr_package[@]};cont++)); do
    pacote=$(dpkg --get-selections | grep "${arr_package[$cont]}" )

    echo -e "verificando se ${arr_package[$cont]} está instalado... ${YEL}[AGUARDE]${WHI}"
    sleep 3

    if [ -n "$pacote" ] ;
    then 
        echo -e "pacote ${arr_package[$cont]} ja instalado ${GRE}[OK]${WHI}"
        sleep 3
    else 
        echo -e "pacote ${RED}${arr_package[$cont]} ${RED}não instalado${WHI}"
        sleep 3
        echo -e "instalando automaticamente... ${YEL}[AGUARDE]${WHI}"
        sleep 3
        echo -e "pacote ${GRE}${arr_package[$cont]} instalado com ${GRE}sucesso!${WHI}"
        sleep 3
    fi
done

FILE=LynxCoderJar

if [ -e "$FILE" ] ; then
    echo -e "o arquivo lynx coder jar ${GRE}já existe${WHI}"
    sleep 3
else
    echo -e "o arquivo lynx coder jar ${RED}não existe${WHI}"
    sleep 3
    echo "adicionando arquivo jar de execução..."
    git clone https://github.com/fabianeheckmann/LynxCoderJar.git
fi

echo
echo -n "deseja entrar na aplicação? [y/n] "
read nome 

if [ "$nome" = "y" ] ; 
then
    echo -e "abrindo aplicação... ${YEL}[AGUARDE]${WHI}"
    sleep 3

    cd LynxCoderJar

    java -jar lynxcoder.jar
else 
    echo -e "\nPressione qualquer tecla para sair..."
fi

read
exit

