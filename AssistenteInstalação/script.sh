#!/bin/bash
echo "Assistente de instalação v1.0.0"

echo "atualizando lista de pacotes e programas instalados.. [aguarde]"
sleep 3

sudo apt update && sudo apt upgrade

echo "lista de pacotes atualizados [OK]"

arr_package=('default-jre')

for((cont=0;cont<${#arr_package[@]};cont++)); do
    pacote=$(dpkg --get-selections | grep "${arr_package[$cont]}" )

    echo "verificando se ${arr_package[$cont]} está instalado... [aguarde]"
    sleep 3

    if [ -n "$pacote" ] ;
    then 
        echo "pacote ${arr_package[$cont]} ja instalado [OK]"
    else 
        echo "pacote ${arr_package[$cont]} não instalado"
        echo "instalando automaticamente... [aguarde]"
        sleep 3

        sudo apt-get install $nome
        
        echo "pacote ${arr_package[$cont]} instalado com sucesso!"
    fi
done

echo -n "Pressione qualquer tecla para sair..."
read
exit
