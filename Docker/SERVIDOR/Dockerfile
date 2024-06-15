FROM ubuntu:latest
RUN apt -y update
RUN apt install -y net-tools inetutils-ping 
RUN apt install -y openjdk-21-jdk
RUN mkdir /app
COPY SERVIDOR.jar /app/


# PASSO A PASSO PARA RODAR O DOCKER E A APLICAÇÃO # 
# -> docker build -imagem .# - (cria uma imagem)
# -> docker run -it imagem (incia um conteiner a partir da imagem)
# -> cd app # - (entra no diretório /app onde esta localizado o .jar)
# -> java -jar SERVIDOR.jar ou CLIENTE.jar # - (executa a aplicação)