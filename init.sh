#!/bin/bash

# Verificando se o java está instalado
if ! command -v java >/dev/null 2>&1; then
    printf '\n'
    printf '=============================================================================\n'
    printf '                      O JAVA NÃO ESTÁ INSTALADO                              \n'
    printf '=============================================================================\n'
    printf '\n'
    exit 1
fi

printf '\n'
printf '=============================================================================\n'
printf '                              LIMPANDO CACHE                                 \n'
printf '=============================================================================\n'
printf '\n'

rm -fr ./target
mvn clean package
mv ./target/*.jar ./target/app.jar

#Building and starting docker image with application inside
printf '\n'
printf '=============================================================================\n'
printf '                           INICIANDO APLICAÇÃO                               \n'
printf '=============================================================================\n'
printf '\n'

mvn spring-boot:run

printf '\n'
printf 'http://localhost:8080/sacode/api/v1.0.0/login                           -> Aplicação Principal\n'
printf 'http://localhost:8080/sacode/api/v1.0.0/swagger-ui/index.html           -> OpenAPI\n'
printf 'http://localhost:8081/                                                  -> Aplicação de monitoramento\n'
printf '\n'
