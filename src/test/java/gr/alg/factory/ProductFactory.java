package gr.alg.factory;

import gr.alg.dto.request.CreateProductDto;
import java.math.BigDecimal;

public final class ProductFactory {
  public static CreateProductDto createProductDto(String productName) {
    return CreateProductDto.builder()
        .productName(productName)
        .description(productName)
        .price(BigDecimal.valueOf(10.5))
        .stock(5)
        .build();
  }

  public static CreateProductDto createProductDto(String productName, BigDecimal price, int stock) {
    return createProductDto(productName).toBuilder()
        .price(price)
        .stock(stock)
        .build();
  }
}
