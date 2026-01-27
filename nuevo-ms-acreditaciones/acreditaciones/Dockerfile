
# ===== Build stage =====
FROM eclipse-temurin:25-jdk AS build
WORKDIR /workspace

# Copiá wrapper y metadata primero para cachear dependencias
COPY mvnw ./
COPY .mvn .mvn
COPY pom.xml .

# 🔧 Fix permisos + finales de línea (CRLF -> LF)
RUN chmod +x mvnw && sed -i 's/\r$//' mvnw

# (Opcional) si tu imagen tiene dos2unix:
# RUN apk add --no-cache dos2unix || microdnf install -y dos2unix || apt-get update && apt-get install -y dos2unix && dos2unix mvnw

# Descarga dependencias offline
RUN ./mvnw -q -B -DskipTests dependency:go-offline

# Copiá el código de la app y construí
COPY src src
RUN ./mvnw -q -B -DskipTests package

# ===== Runtime stage =====
FROM eclipse-temurin:25-jre
WORKDIR /app
COPY --from=build /workspace/target/*.jar /app/ms2-app.jar
ENV JAVA_TOOL_OPTIONS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"
EXPOSE 8081
ENTRYPOINT ["sh","-c","java $JAVA_TOOL_OPTIONS -jar /app/ms2-app.jar"]