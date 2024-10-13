package gr.alg.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CreateProductDto {
  @NotBlank(message = "product name is mandatory")
  private String productName;

  @NotBlank(message = "product description cannot be empty")
  private String description;

  @NotNull(message = "price cannot be blank")
  private BigDecimal price;

  @NotNull(message = "stock cannot be blank")
  private int stock;
}
