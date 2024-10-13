package gr.alg.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;
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
public class NewOrderDto {
  @Min(value = 1, message = "User ID is mandatory")
  private Long userId;

  @NotEmpty(message = "Order items list cannot be empty")
  @Size(min = 1, message = "Order must contain at least one item")
  private List<OrderItemDto> orderItems;
}
