package gr.alg.service;

import com.speedment.jpastreamer.application.JPAStreamer;
import gr.alg.dto.request.UserRegistrationDto;
import gr.alg.entity.UserEntity;
import gr.alg.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Transactional
@ApplicationScoped
@RequiredArgsConstructor
public class BookService {

  private final UserRepository userRepository;
  private final JPAStreamer jpaStreamer;

  public void createUser(UserRegistrationDto userRegistrationDto) {
    var newUser = UserEntity
        .builder()
        .email(userRegistrationDto.getEmail())
        .username(userRegistrationDto.getUsername())
        .password(userRegistrationDto.getPassword())
        .build();

    userRepository.persist(newUser);
  }

  public UserRegistrationDto findUser(String username) {
    var userEntity = userRepository.findByUsername(username);

    var userDto = UserRegistrationDto
        .builder()
        .email(userEntity.getEmail())
        .username(userEntity.getUsername())
        .password(userEntity.getPassword())
        .build();

    return userDto;
  }
}
