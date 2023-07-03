# Imagem base do Java 17.0.7
FROM openjdk:17

# Copie o arquivo JAR e o arquivo application.properties para dentro do contêiner
COPY target/F1-Backend-0.0.1-SNAPSHOT.jar /app/f1-backend.jar
COPY src/main/resources/application.properties /app/application.properties

# Defina o diretório de trabalho como /app
WORKDIR /app

# Expõe a porta que a API está escutando
EXPOSE 8080

# Comando para executar a aplicação quando o contêiner for iniciado
CMD ["java", "-jar", "/app/f1-backend.jar", "--spring.config.location=file:/app/application.properties"]
