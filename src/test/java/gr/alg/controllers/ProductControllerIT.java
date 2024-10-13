package gr.alg.controllers;

import static gr.alg.constants.ControllerConstants.API_V1;
import static gr.alg.constants.ControllerConstants.CREATE;
import static gr.alg.constants.ControllerConstants.FIND_PRODUCT;
import static gr.alg.constants.ControllerConstants.PRODUCT;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import gr.alg.configuration.BaseIntegrationTest;
import gr.alg.dto.request.CreateProductDto;
import gr.alg.repository.OrderItemRepository;
import gr.alg.repository.OrderRepository;
import gr.alg.repository.ProductRepository;
import gr.alg.repository.UserRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response.Status;
import java.math.BigDecimal;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductControllerIT extends BaseIntegrationTest {

  @Inject UserRepository userRepository;
  @Inject OrderRepository orderRepository;
  @Inject OrderItemRepository orderItemRepository;
  @Inject ProductRepository productRepository;

  @BeforeEach
  @Transactional
  void setUp() {
    orderItemRepository.deleteAll();
    orderRepository.deleteAll();
    productRepository.deleteAll();
    userRepository.deleteAll();
  }

  @Test
  @SneakyThrows
  void successfulProductCreation() {
//    var userDto = UserRegistrationDto.builder()
//        .email("user@test.gr")
//        .username("testuser")
//        .password("pass123")
//        .build();

    // todo add user roles. only users with specific roles can create products

//    given()
//        .contentType(ContentType.JSON)
//        .body(userDto)
//    .when()
//        .post("/api/v1/user/register")
//    .then()
//        .statusCode(201);

//    var totalUsers = userRepository.findAll().list().size();
//    Assertions.assertEquals(1, totalUsers);

    var productDto = CreateProductDto.builder()
        .productName("test-product")
        .description("description")
        .stock(5)
        .price(BigDecimal.valueOf(15.5))
        .build();

    createProduct(productDto);

    var totalProducts = productRepository.findAll().list().size();
    Assertions.assertEquals(1, totalProducts);
  }

  @Test
  @SneakyThrows
  void successfulProductFetch() {
    var productDto = CreateProductDto.builder()
        .productName("test-product")
        .description("description")
        .stock(5)
        .price(BigDecimal.valueOf(15.5))
        .build();

    createProduct(productDto);

    var dbProducts = productRepository.findAll().list();
    var dbProduct = dbProducts.getFirst();
    var totalProducts = productRepository.findAll().list().size();

    Assertions.assertEquals(productDto.getProductName(), dbProduct.getProductName());
    Assertions.assertEquals(1, totalProducts);

    given()
        .contentType(ContentType.JSON)
    .when()
        .get(API_V1 + PRODUCT + FIND_PRODUCT + "/" + dbProduct.getId())
    .then()
        .statusCode(Status.FOUND.getStatusCode())
        .body("description", is(productDto.getDescription()))
        .log().ifError();
  }

  private void createProduct(CreateProductDto productDto) {
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