package gr.alg.mappers;

import gr.alg.dto.request.NewOrderDto;
import gr.alg.entity.OrderEntity;
import org.mapstruct.Mapper;

/**
 * Converts Order DTOs to domain objects and vice versa.
 */
@Mapper(componentModel = "cdi")
public interface OrderMapper {
  NewOrderDto toUserResponseDto(OrderEntity entity);
}
