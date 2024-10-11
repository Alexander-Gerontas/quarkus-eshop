package gr.alg.service;

import gr.alg.dto.request.UserRegistrationDto;
import gr.alg.dto.response.UserResponseDto;
import gr.alg.mappers.UserMapper;
import gr.alg.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Transactional
@ApplicationScoped
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final UserMapper userMapper;

  public void createUser(UserRegistrationDto userRegistrationDto) {
    var newUser = userMapper.fromDto(userRegistrationDto);

    // todo add registration date
    userRepository.persist(newUser);
  }

  public UserResponseDto findUser(String username) {
    var userEntity = userRepository.findByUsername(username);

    var userDto = userMapper.toResponseDto(userEntity);

    return userDto;
  }
}
