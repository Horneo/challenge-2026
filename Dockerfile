
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

RUN ./mvnw -q -B -DskipTests -Dproject.build.sourceEncoding=UTF-8 package

# ===== Runtime stage =====
FROM eclipse-temurin:25-jre
WORKDIR /app
COPY --from=build /workspace/target/*.jar /app/app.jar

ENV JAVA_TOOL_OPTIONS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"
ENV MAVEN_OPTS="-Dfile.encoding=UTF-8"
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]

