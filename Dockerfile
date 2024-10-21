# Этап сборки (Build stage)
FROM maven:3.9.9-eclipse-temurin-21 as build


# Установка curl для загрузки MongoDB Shell
RUN apt-get update && apt-get install -y curl

# Загрузка и установка MongoDB Shell
RUN curl -fsSL https://downloads.mongodb.com/compass/mongosh-2.3.2-linux-x64.tgz | tar -xzv -C /tmp

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем pom.xml и скачиваем зависимости
COPY pom.xml .

# Скачиваем зависимости, чтобы избежать повторной загрузки на каждом этапе
RUN mvn dependency:go-offline

# Копируем исходный код проекта в контейнер
COPY src ./src

# Сборка проекта (выводим jar-файл в target)
RUN mvn clean package -DskipTests

# Этап исполнения (Runtime stage)
FROM openjdk:21-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем собранный JAR файл из предыдущего этапа
COPY --from=build /app/target/*.jar /app/app.jar

# Открываем порт 8080 для приложения
EXPOSE 8080

# Указываем команду для запуска приложения
ENTRYPOINT ["java", "-jar", "/app/app.jar"]