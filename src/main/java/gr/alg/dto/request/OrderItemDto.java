package gr.alg.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class OrderItemDto {
  @Min(value = 1, message = "Item ID must be greater than 0")
  private long itemId;

  @Min(value = 1, message = "Quantity must be greater than 0")
  @Max(value = 1, message = "You cannot buy more than 100 items")
  private int quantity;
}
