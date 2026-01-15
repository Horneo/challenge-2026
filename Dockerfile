FROM ubuntu:latest
LABEL authors="rodriguez.g.nicolas"

# Usar una imagen base de Java 25
FROM eclipse-temurin:25-jdk-alpine

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /point_of_sale_app

# Copiar el archivo JAR generado por Maven/Gradle al contenedor
COPY target/punto-de-venta-acc-0.0.1-SNAPSHOT.jar point_of_sale_app.jar

# Exponer el puerto en el que corre la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "point_of_sale_app.jar"]
