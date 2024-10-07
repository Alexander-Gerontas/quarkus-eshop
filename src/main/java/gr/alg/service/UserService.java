package gr.alg.service;

import com.speedment.jpastreamer.application.JPAStreamer;
import gr.alg.entity.UserDto;
import gr.alg.entity.UserEntity;
import gr.alg.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Transactional
@ApplicationScoped
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final JPAStreamer jpaStreamer;

  public void createUser(UserDto userDto) {
    var newUser = UserEntity
        .builder()
        .email(userDto.getEmail())
        .username(userDto.getUsername())
        .password(userDto.getPassword())
        .build();

    userRepository.persist(newUser);
  }

  public UserDto findUser(String username) {
    var userEntity = userRepository.findByUsername(username);

    var userDto = UserDto
        .builder()
        .email(userEntity.getEmail())
        .username(userEntity.getUsername())
        .password(userEntity.getPassword())
        .build();

    return userDto;
  }
}
