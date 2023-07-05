# Estágio de build
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app

# Copie o arquivo pom.xml para o contêiner
COPY pom.xml .

# Baixe as dependências do Maven
RUN mvn dependency:go-offline

# Copie todo o código-fonte para o contêiner
COPY src ./src

# Compile o projeto
RUN mvn package -DskipTests

# Estágio final
FROM openjdk:17

# Copie o arquivo JAR gerado no estágio de build para dentro do contêiner
COPY --from=build /app/target/F1-Backend-0.0.1-SNAPSHOT.jar /app/f1-backend.jar

# Defina o diretório de trabalho como /app
WORKDIR /app

# Expõe a porta que a API está escutando
EXPOSE 8080

# Comando para executar a aplicação quando o contêiner for iniciado
CMD ["java", "-jar", "/app/f1-backend.jar", "--spring.profiles.active=prd"]

