# Тестовое задание
## Исходное задание
Разработать приложение на java 21, которое анализирует данные из xml-файла ,сохраняет
в MongoDB и предоставляет возможность асинхронного доступа к обработанным данным
по REST API.
```xml
<?xml version="1.0" encoding="UTF-8"?>
<users>
    <user id="1">
        <personalinfo>
            <firstname>John</firstname>
            <lastname>Doe</lastname>
            <email>john.doe@example.com</email>
            <dateofbirth>1990-01-01</dateofbirth>
            <gender>Male</gender>
        </personalinfo>
        <contactinfo>
            <phonenumber>+1234567890</phonenumber>
            <address>
                <street>123 Main St</street>
                <city>New York</city>
                <state>NY</state>
                <postalcode>10001</postalcode>
                <country>USA</country>
            </address>
        </contactinfo>
        <employment>
            <companyname>Example Corp</companyname>
            <position>Software Engineer</position>
            <startdate>2015-06-01</startdate>
            <enddate>Present</enddate>
        </employment>
        <education>
            <universityname>Example University</universityname>
            <degree>Bachelor's in Computer Science</degree>
            <graduationyear>2014</graduationyear>
        </education>
        <skills>
            <skill>Java</skill>
            <skill>Spring Boot</skill>
            <skill>MongoDB</skill>
            <skill>REST API</skill>
        </skills>
    </user>
</users>
```
### Задачи:
1. Приложение должно читать XML-файлы асинхронно, используя многопоточность.
2. Извлеченные данные необходимо сохранить в базу MongoDB.
3. Реализовать REST API для CRUD операций.
4. Реализовать демонстрацию функционала посредством SWAGGER UI.
### Требования к заданию:
1. Для работы с базой данных использовать официальный драйвер MongoDB.
2. Для написания приложения и разработки REST API использовать spring boot
3. Для парсинга xml использовать наиболее подходящий парсер – указать использованный
парсер.
### Дополнительные задачи:
1. Добавьте аутентификацию для доступа к REST API – любой возможный.
2. Реализовать обработку больших XML файлов с минимальным потреблением памяти.
3. Использовать Docker для контейнеризации приложения и базы данных и предоставить
инструкцию по запуску приложения.
4. Написать unit тесты на проверку компонентов схемы.
## Реализация
### Приложение использет
1. Spring boot 3.3.4
2. Java 21.0.4
3. maven 3.9.9
4. docker
### API
Апи защищено базовой системой аунтафикации по логину и паролю. Реализованно асинхронное выполнение запросов для users.
### Итоговое апи можно посмотреть по следующим url
1. https://petstore.swagger.io/
С указанием схраненного swagger json
https://raw.githubusercontent.com/nerfenoff/TestTask/refs/heads/master/manualSwagger.json
2. http://127.0.0.1:8080/swagger-ui/index.html При локальной отладке приложения по url 
### XML парсеры
В приложение присутсвует 2 XML парсера.
1. Jakarta XML доступный по пост запросу `/api/v1/users`
2. StAX доступный по пост запросу `/api/v1/users/largeXml`
StaX является парсером с минимальным потреблением памяти т.к. осуществляет обработку во время чтения xml.
### Тестирование
Приложение тестировалось с использованием unit тестов и Postman
#### Unit тесты
Для тестирования приложения были написаны unit тесты которые описаны в com.testTask.Test.UsersServiceTest
всего их 9.
Приложение прошло проверку

![image](https://github.com/user-attachments/assets/4ae28a18-ed82-4ff3-9ab8-ad5274403e74)
#### Postman
Все api запросы были протестированы в postman

1.Тестирование с указанием авторизации

![image](https://github.com/user-attachments/assets/bf306274-8ee2-46d8-885b-ebde17d5da8f)
Как видно на изображении выше, запрос успешно выполнен

2. Тестирование без авторизации

![image](https://github.com/user-attachments/assets/adf9f1a9-8743-49b8-83e3-963718abcab9)
Как видно на изображении выше, запрос не был выполнен и получен код 401
#### Новый пользователей для api
Добавить нового пользователя можно через пост запрос
Запрос принмает JSON с данными для авторизации

![image](https://github.com/user-attachments/assets/29d7e961-5804-4b3f-aa8e-67496a0b5760)

### docker
Приложение использует докер. Для его настройки созданы 2 файла в корне проекта `Dockerfile` и `docker-compose.yml`.
Для запуска приложения необходимо внести корректировки в них под данные сервер, а так же в терминале выполните комманду `docker-compose up --build` для сборки докера. Чтобы остановить и удалить контейнеры, выполните `docker-compose down`

После запуска приложения будет создано 2 контейнера

![image](https://github.com/user-attachments/assets/dc8a7d97-f334-4a42-9ce5-2237479b45f0)


