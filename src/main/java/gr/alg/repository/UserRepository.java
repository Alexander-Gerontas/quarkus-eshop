package gr.alg.repository;

import gr.alg.entity.UserEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements PanacheRepository<UserEntity> {
  public UserEntity findByUsername(String username) {
    return find("username", username).firstResult();
  }
}
