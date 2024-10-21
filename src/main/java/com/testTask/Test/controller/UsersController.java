package com.testTask.Test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.testTask.Test.models.User;
import com.testTask.Test.service.UsersService;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@OpenAPIDefinition(info = @Info(title = "/* API для работы с пользователем */", version = "v1"))
@SecurityRequirement(name = "basicAuth")
@RequestMapping("/api/v1/users")
public class UsersController {

    @Autowired
    private UsersService userService;

    @Operation(summary = "Получить всех пользователей")
    @GetMapping
    public CompletableFuture<ResponseEntity<List<User>>> getAllUser() {
        return userService.getAll()
                .thenApply(ResponseEntity::ok);
    }

    @Operation(summary = "Получить конкретного пользователя по его id")
    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<Optional<User>>> getAllUser(
            @Parameter(description = "id пользователя") @PathVariable String id) {
        return userService.findUser(id)
                .thenApply(ResponseEntity::ok);
    }

    @Operation(summary = "Загрузить пользователей через xml. Парсер Jakarta XML")
    @PostMapping
    public CompletableFuture<ResponseEntity<String>> uploadXml(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "XML пользователей", required = true, content = @Content(mediaType = "application/xml", schema = @Schema(implementation = User.class), examples = @ExampleObject(value = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><users><user id=\"1\"><personalinfo><firstname>John</firstname><lastname>Doe</lastname><email>john.doe@example.com</email><dateofbirth>1990-01-01</dateofbirth><gender>Male</gender></personalinfo><contactinfo><phonenumber>+1234567890</phonenumber><address><street>123 Main St</street><city>New York</city><state>NY</state><postalcode>10001</postalcode><country>USA</country></address></contactinfo><employment><companyname>Example Corp</companyname><position>Software Engineer</position><startdate>2015-06-01</startdate><enddate>Present</enddate></employment><education><universityname>Example University</universityname><degree>Bachelor's in Computer Science</degree><graduationyear>2014</graduationyear></education><skills><skill>Java</skill><skill>Spring Boot</skill><skill>MongoDB</skill><skill>REST API</skill></skills></user></users>\r\n"))) @RequestBody String xmlString) {
        return userService.parseXml(xmlString).thenApply(ResponseEntity::ok);
    }

    @Operation(summary = "Загрузить пользователей через xml. Парсер StAX")
    @PostMapping("/largeXml")
    public CompletableFuture<ResponseEntity<String>> uploadLargeXml(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "XML пользователей", required = true, content = @Content(mediaType = "application/xml", schema = @Schema(implementation = User.class), examples = @ExampleObject(value = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><users><user id=\"1\"><personalinfo><firstname>John</firstname><lastname>Doe</lastname><email>john.doe@example.com</email><dateofbirth>1990-01-01</dateofbirth><gender>Male</gender></personalinfo><contactinfo><phonenumber>+1234567890</phonenumber><address><street>123 Main St</street><city>New York</city><state>NY</state><postalcode>10001</postalcode><country>USA</country></address></contactinfo><employment><companyname>Example Corp</companyname><position>Software Engineer</position><startdate>2015-06-01</startdate><enddate>Present</enddate></employment><education><universityname>Example University</universityname><degree>Bachelor's in Computer Science</degree><graduationyear>2014</graduationyear></education><skills><skill>Java</skill><skill>Spring Boot</skill><skill>MongoDB</skill><skill>REST API</skill></skills></user></users>\r\n"))) @RequestBody String xmlString) {
        return userService.parseLargeXml(xmlString).thenApply(ResponseEntity::ok);
    }

    @Operation(summary = "Обновить пользователя по его id")
    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity<String>> updateUser(
            @Parameter(description = "id пользователя") @PathVariable String id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "XML пользователей", required = true, content = @Content(mediaType = "application/xml", schema = @Schema(implementation = User.class), examples = @ExampleObject(value = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><user id=\"2\"><personalinfo><firstname>John2new</firstname><lastname>Doe</lastname><email>john2.doe@example.com</email><dateofbirth>1990-01-02</dateofbirth><gender>Male</gender></personalinfo><contactinfo><phonenumber>+1234567892</phonenumber><address><street>1234 Main St</street><city>New York</city><state>NY</state><postalcode>10001</postalcode><country>USA</country></address></contactinfo><employment><companyname>Example Corp</companyname><position>Software Engineer</position><startdate>2015-06-01</startdate><enddate>Present</enddate></employment><education><universityname>Example University</universityname><degree>Bachelor's in Computer Science</degree><graduationyear>2014</graduationyear></education><skills><skill>Java</skill><skill>Spring Boot</skill><skill>MongoDB</skill><skill>REST API</skill></skills></user>\r\n"))) @RequestBody String entity) {
        return userService.updateUser(id, entity).thenApply(ResponseEntity::ok);
    }

    @Operation(summary = "Удалить пользователя по его id")
    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<String>> deleteUser(
            @Parameter(description = "id пользователя") @PathVariable String id) {
        return userService.deleteUser(id)
                .thenApply(ResponseEntity::ok);
    }
}
