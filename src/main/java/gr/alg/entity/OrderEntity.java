package gr.alg.entity;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Table(name = "orders")
public class OrderEntity {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id", updatable = false, nullable = false)
  private Long id;

  @ManyToOne
  private UserEntity customer;

  @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private List<OrderItemEntity> orderItems = new ArrayList<>();

  @Column(name = "placed_at", nullable = false)
  private LocalDateTime placedAt;

  public void addOrderItem(OrderItemEntity item) {
    orderItems.add(item);
    item.setOrder(this);  // Link the order to the item
  }

  public void addOrderItems(List<OrderItemEntity> items) {
    items.forEach(this::addOrderItem);
  }
}
