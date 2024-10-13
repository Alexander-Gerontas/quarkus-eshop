package gr.alg.crud;

import static gr.alg.constants.ControllerConstants.API_V1;
import static gr.alg.constants.ControllerConstants.CREATE;
import static gr.alg.constants.ControllerConstants.PRODUCT;
import static io.restassured.RestAssured.given;

import gr.alg.dto.request.CreateProductDto;
import io.restassured.http.ContentType;
import jakarta.ws.rs.core.Response.Status;

public final class ProductCrud {
  public static void createProduct(CreateProductDto productDto) {
    given()
        .contentType(ContentType.JSON)
        .body(productDto)
        .when()
        .post(API_V1 + PRODUCT + CREATE)
        .then()
        .statusCode(Status.CREATED.getStatusCode())
        .log().ifError();
  }
}
