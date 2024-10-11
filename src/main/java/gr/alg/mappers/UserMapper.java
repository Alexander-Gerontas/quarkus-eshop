package gr.alg.mappers;

import gr.alg.dto.request.UserRegistrationDto;
import gr.alg.dto.response.UserResponseDto;
import gr.alg.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Converts Product DTOs to domain objects and vice versa.
 */
@Mapper(componentModel = "cdi")
public interface UserMapper {
  UserEntity fromDto(UserRegistrationDto dto);

  @Mapping(source = "entity.id", target = "userId")
  UserResponseDto toResponseDto(UserEntity entity);
}
