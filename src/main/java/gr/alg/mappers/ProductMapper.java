package gr.alg.mappers;

import gr.alg.dto.request.CreateProductDto;
import gr.alg.dto.response.ProductResponseDto;
import gr.alg.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Converts Product DTOs to domain objects and vice versa.
 */
@Mapper(componentModel = "cdi")
public interface ProductMapper {
  ProductEntity fromDto(CreateProductDto dto);

  @Mapping(source = "entity.id", target = "productId")
  ProductResponseDto toResponseDto(ProductEntity entity);
}
