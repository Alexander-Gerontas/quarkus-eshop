package gr.alg.entity;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Table(name = "products")
public class ProductEntity {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id", updatable = false, nullable = false)
  private Long id;

  @Column(name = "product_name", updatable = false, nullable = false)
  private String productName;

  @Column(name = "description", updatable = false, nullable = false)
  private String description;

  @Column(nullable = false)
  private BigDecimal price;

  @Column(name = "stock", nullable = false)
  private long stock;

  @Column(name = "created_at", updatable = false, nullable = false)
  private LocalDate createdAt;
}
