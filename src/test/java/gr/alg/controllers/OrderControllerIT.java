package gr.alg.controllers;

import static gr.alg.constants.ControllerConstants.API_V1;
import static gr.alg.constants.ControllerConstants.CREATE;
import static gr.alg.constants.ControllerConstants.ORDER;
import static io.restassured.RestAssured.given;

import gr.alg.configuration.BaseIntegrationTest;
import gr.alg.crud.ProductCrud;
import gr.alg.crud.UserCrud;
import gr.alg.dto.request.NewOrderDto;
import gr.alg.dto.request.OrderItemDto;
import gr.alg.dto.request.UserRegistrationDto;
import gr.alg.entity.ProductEntity;
import gr.alg.factory.OrderFactory;
import gr.alg.factory.ProductFactory;
import gr.alg.repository.OrderRepository;
import gr.alg.repository.ProductRepository;
import gr.alg.repository.UserRepository;
import gr.alg.service.ProductService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response.Status;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderControllerIT extends BaseIntegrationTest {
  @Inject ProductService productService;

  @Inject UserRepository userRepository;
  @Inject ProductRepository productRepository;
  @Inject OrderRepository orderRepository;

  @Test
  @SneakyThrows
  void successfulOrderCreation() {
    // todo specific users can create orders

    var productDtos = List.of(
        ProductFactory.createProductDto("prod1"),
        ProductFactory.createProductDto("prod2"),
        ProductFactory.createProductDto("prod3")
    );

    // register products
    productDtos.forEach(ProductCrud::createProduct);

    var dbProducts = productRepository.findAll().list();
    var totalProducts = dbProducts.size();

    var productIds = dbProducts.stream()
        .map(ProductEntity::getId)
        .collect(Collectors.toSet());

    Assertions.assertEquals(3, totalProducts);

    var userDto = UserRegistrationDto.builder()
        .email("user@test.gr")
        .username("testuser")
        .password("pass123")
        .build();

    UserCrud.registerUser(userDto);

    var dbUsers = userRepository.findAll().list();
    var totalUsers = dbUsers.size();
    var userEntity = dbUsers.getFirst();

    Assertions.assertEquals(userDto.getEmail(), userEntity.getEmail());
    Assertions.assertEquals(1, totalUsers);

    var orderItemDtos = OrderFactory.createOrderItemDtos(productIds);

    var orderDto = NewOrderDto.builder()
        .userId(userEntity.getId())
        .orderItems(orderItemDtos)
        .build();

    // place the order
    given()
        .contentType(ContentType.JSON)
        .body(orderDto)
        .when()
        .post(API_V1 + ORDER + CREATE)
        .then()
        .statusCode(Status.CREATED.getStatusCode())
        .log().ifError();

    // verify order is placed
    var order = orderRepository.findAll().list().getFirst();

    Assertions.assertEquals(order.getCustomer().getId(), userEntity.getId());
    Assertions.assertEquals(order.getOrderItems().size(), orderItemDtos.size());

    // check product quantity
    var updatedDbProducts = productService.getAllProducts();

    var updatedDbProductsMap = updatedDbProducts.stream()
        .collect(Collectors.toMap(ProductEntity::getId, Function.identity()));

    var orderItemQuantityMap = orderItemDtos.stream()
            .collect(Collectors.toMap(OrderItemDto::getItemId, OrderItemDto::getQuantity));

    dbProducts.forEach(dbProduct -> {
      var orderQuant = orderItemQuantityMap.get(dbProduct.getId());
      var updatedProduct = updatedDbProductsMap.get(dbProduct.getId());

      Assertions.assertEquals(dbProduct.getStock() - orderQuant, updatedProduct.getStock());
    });
  }
}