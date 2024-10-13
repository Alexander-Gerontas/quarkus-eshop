package gr.alg.factory;

import gr.alg.dto.request.OrderItemDto;
import java.util.List;
import java.util.Set;

public final class OrderFactory {
  public static List<OrderItemDto> createOrderItemDtos(Set<Long> productDto) {
    return productDto.stream()
        .map(productId -> new OrderItemDto(productId, productId.intValue() + 1))
        .toList();
  }
}
