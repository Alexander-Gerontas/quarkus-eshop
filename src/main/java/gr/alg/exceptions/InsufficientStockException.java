package gr.alg.exceptions;

import jakarta.ws.rs.core.Response.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InsufficientStockException extends CustomException {
  public InsufficientStockException(Long productId, int stock, int requiredQuantity) {
    super("Insufficient stock for product ID: " + productId +
        ". Available: " + stock + ", Required: " + requiredQuantity, Status.NOT_ACCEPTABLE);
  }
}
