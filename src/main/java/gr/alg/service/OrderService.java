package gr.alg.service;

import gr.alg.dto.request.NewOrderDto;
import gr.alg.dto.request.OrderItemDto;
import gr.alg.entity.OrderEntity;
import gr.alg.entity.OrderItemEntity;
import gr.alg.entity.ProductEntity;
import gr.alg.exceptions.InsufficientStockException;
import gr.alg.repository.OrderRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@Transactional
@ApplicationScoped
@RequiredArgsConstructor
public class OrderService {
  private final UserService userService;
  private final ProductService productService;
  private final OrderRepository orderRepository;

  public void createOrder(NewOrderDto orderDto) {
    // Fetch the customer (user)
    var customer = userService.findUserById(orderDto.getUserId());

    // Extract product IDs from the order
    var requestedProductIds = orderDto.getOrderItems().stream()
        .map(OrderItemDto::getItemId)
        .toList();

    var productEntities = productService.findProductsById(requestedProductIds);

    var productEntityMap = productEntities.stream()
        .collect(Collectors.toMap(ProductEntity::getId, Function.identity()));

    // check if quantity requested is sufficient
    orderDto.getOrderItems().stream()
        .filter(orderItemDto -> {
          var productEntity = productEntityMap.get(orderItemDto.getItemId());
          var quantity = orderItemDto.getQuantity();

          return (productEntity.getStock() < quantity);
        })
        .findAny()
        .ifPresent(orderItemDto -> {
          throw new InsufficientStockException(
              orderItemDto.getItemId(),
              productEntityMap.get(orderItemDto.getItemId()).getStock(),
              orderItemDto.getQuantity()
          );
        });

    // create order entity
    var newOrder = OrderEntity.builder()
        .customer(customer)
        .placedAt(LocalDateTime.now())
        .build();

    // create order items
    var orderItems = orderDto.getOrderItems().stream()
        .map(orderItemDto -> {
          var productEntity = productEntityMap.get(orderItemDto.getItemId());

          return OrderItemEntity.builder()
              .order(newOrder)
              .product(productEntity)
              .quantity(orderItemDto.getQuantity())
              .unitPurchasePrice(productEntity.getPrice())
              .build();
        })
        .collect(Collectors.toList());

    // set order items to order
    newOrder.setOrderItems(orderItems);

    // persist order
    orderRepository.persist(newOrder);

    var orderItemDtoMap = orderDto.getOrderItems().stream()
        .collect(Collectors.toMap(OrderItemDto::getItemId, OrderItemDto::getQuantity));

    // Update product stock after the order is created
    productEntities.forEach(productEntity -> {
      var orderQuantity = orderItemDtoMap.get(productEntity.getId());

      // Reduce the stock of the product
      productEntity.setStock(productEntity.getStock() - orderQuantity);
    });

    productService.updateProducts(productEntities);  // Save the updated product stock
  }
}
