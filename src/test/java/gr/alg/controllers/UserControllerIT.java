package gr.alg.controllers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import gr.alg.configuration.BaseIntegrationTest;
import gr.alg.dto.request.UserRegistrationDto;
import gr.alg.repository.UserRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response.Status;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerIT extends BaseIntegrationTest {

  @Inject
  UserRepository userRepository;

  @Test
  @SneakyThrows
  void successfulUserRegistration() {
    var userDto = UserRegistrationDto.builder()
        .email("user@test.gr")
        .username("testuser")
        .password("pass123")
        .build();

    given()
        .contentType(ContentType.JSON)
        .body(userDto)
    .when()
        .post("/api/v1/user/register")
    .then()
        .statusCode(201);

    var totalUsers = userRepository.findAll().list().size();
    Assertions.assertEquals(1, totalUsers);
  }

  @Test
  @SneakyThrows
  void successfulUserFetch() {
    var userDto = UserRegistrationDto.builder()
        .email("user@test.gr")
        .username("testuser")
        .password("pass123")
        .build();

    given()
        .contentType(ContentType.JSON)
        .body(userDto)
    .when()
        .post("/api/v1/user/register")
    .then()
        .statusCode(201);

    given()
        .contentType(ContentType.JSON)
    .when()
        .get("/api/v1/user/find-user/" + userDto.getUsername())
    .then()
        .statusCode(Status.FOUND.getStatusCode())
        .body("username", is(userDto.getUsername()))
    ;
  }
}