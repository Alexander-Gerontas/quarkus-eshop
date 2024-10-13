package gr.alg.controllers;

import static gr.alg.constants.ControllerConstants.API_V1;
import static gr.alg.constants.ControllerConstants.FIND_USER;
import static gr.alg.constants.ControllerConstants.REGISTER;
import static gr.alg.constants.ControllerConstants.USER;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import gr.alg.configuration.BaseIntegrationTest;
import gr.alg.dto.request.UserRegistrationDto;
import gr.alg.repository.UserRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response.Status;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerIT extends BaseIntegrationTest {
  @Inject
  UserRepository userRepository;

  @BeforeEach
  @Transactional
  void setUp() {
    userRepository.deleteAll();
  }

  @Test
  @SneakyThrows
  void successfulUserRegistration() {
    var userDto = UserRegistrationDto.builder()
        .email("user@test.gr")
        .username("testuser")
        .password("pass123")
        .build();

    registerUser(userDto);

    var dbUsers = userRepository.findAll().list();
    var totalUsers = dbUsers.size();
    var userEntity = dbUsers.getFirst();

    Assertions.assertEquals(userDto.getEmail(), userEntity.getEmail());
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

    registerUser(userDto);

    given()
        .contentType(ContentType.JSON)
    .when()
        .get(API_V1 + USER + FIND_USER + "/" + userDto.getUsername())
    .then()
        .statusCode(Status.FOUND.getStatusCode())
        .body("username", is(userDto.getUsername()))
        .log().ifValidationFails(LogDetail.BODY);
  }

  private void registerUser(UserRegistrationDto userDto) {
    given()
        .contentType(ContentType.JSON)
        .body(userDto)
        .when()
        .post(API_V1 + USER + REGISTER)
        .then()
        .statusCode(Status.CREATED.getStatusCode());
  }
}