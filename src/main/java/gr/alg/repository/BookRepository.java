package gr.alg.repository;

import gr.alg.entity.BookEntity;
import gr.alg.entity.UserEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BookRepository implements PanacheRepository<BookEntity> {
  public BookEntity findByAuthor(String authorName) {
    return find("author", authorName).firstResult();
  }
}
