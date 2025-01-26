FROM maven:3.8.5-openjdk-17 AS builder

WORKDIR /build
COPY . .

# Cria o .jar da aplicacao
RUN mvn clean install -DskipTests

WORKDIR /app

COPY --from=builder /build/target/*.jar /app/app.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/app.jar"]
CMD ["mvn", "test"]