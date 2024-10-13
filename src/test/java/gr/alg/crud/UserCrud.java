package gr.alg.crud;

import static gr.alg.constants.ControllerConstants.API_V1;
import static gr.alg.constants.ControllerConstants.REGISTER;
import static gr.alg.constants.ControllerConstants.USER;
import static io.restassured.RestAssured.given;

import gr.alg.dto.request.UserRegistrationDto;
import io.restassured.http.ContentType;
import jakarta.ws.rs.core.Response.Status;

public final class UserCrud {
  public static void registerUser(UserRegistrationDto userDto) {
    given()
        .contentType(ContentType.JSON)
        .body(userDto)
        .when()
        .post(API_V1 + USER + REGISTER)
        .then()
        .statusCode(Status.CREATED.getStatusCode());
  }
}
