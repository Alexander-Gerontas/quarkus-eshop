package gr.alg.repository;

import gr.alg.entity.ProductEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ProductRepository implements PanacheRepository<ProductEntity> {
  public List<ProductEntity> findByIds(List<Long> productIds) {
    return find("id in ?1", productIds).list();
  }
}
