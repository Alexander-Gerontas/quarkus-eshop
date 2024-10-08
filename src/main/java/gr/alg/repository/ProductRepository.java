package gr.alg.repository;

import gr.alg.entity.ProductEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProductRepository implements PanacheRepository<ProductEntity> {

  // todo remove
//  public BookEntity findByAuthor(String authorName) {
//    return find("author", authorName).firstResult();
//  }
}
