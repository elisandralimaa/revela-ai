# Stage 1: Build (Usando Eclipse Temurin JDK 21 como base)
# Esta imagem é maior e contém as ferramentas necessárias para compilar.
FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /app

# 1. Instalar o Maven (Como a imagem não é 'maven:...', precisamos instalar o build tool)
# Para um ambiente Alpine, usamos 'apk add' para instalar dependências.
RUN apk add --no-cache maven

# Copiar arquivos  qessenciais para o build
# pom.xml, mvnw, e o diretório .mvn (que precisa vir antes de usar o mvnw)
COPY ../pom.xml .
COPY ../mvnw .
COPY ../.mvn .mvn

# Garante permissão de execução
RUN chmod +x ./mvnw

# Baixar dependências e compilar (em estágios separados para melhor caching)
RUN ./mvnw dependency:go-offline -B
COPY ../src src
RUN ./mvnw clean package -DskipTests

# Stage 2: Runtime (Otimizado)
# Esta imagem é a menor possível, contendo apenas o JRE para executar.
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copiar JAR da stage anterior
COPY --from=builder /app/target/*.jar app.jar

# Configuração de Ambiente
EXPOSE 8080
# Define limites de memória para evitar o OOMKilled no ambiente de containers
# O Railway cuida de alguns aspectos, mas é uma boa prática
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Comando para iniciar a aplicação
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]