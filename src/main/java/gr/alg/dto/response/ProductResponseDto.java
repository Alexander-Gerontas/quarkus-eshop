package gr.alg.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ProductResponseDto {
  private Long productId;

  private String productName;

  private String description;

  private BigDecimal price;

  private long stock;

  private LocalDate createdAt;
}
