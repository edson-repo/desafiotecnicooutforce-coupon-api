# Usa uma imagem leve com Java 17 para executar a aplicação
FROM eclipse-temurin:17-jre

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia o jar gerado pelo Maven para dentro da imagem
COPY target/coupon-api-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta usada pela aplicação
EXPOSE 8080

# Comando de inicialização da API
ENTRYPOINT ["java", "-jar", "app.jar"]