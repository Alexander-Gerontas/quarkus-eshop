package gr.alg.exceptions;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomException extends RuntimeException {
  private String message;
  private Status status;

  public CustomException(Exception exception) {
    this(exception, Status.BAD_REQUEST);
  }

  public CustomException(Exception exception, Status status) {
    this.status = status;
    this.message = exception.getMessage();
  }

  public CustomException(String message, Status status) {
    this.status = status;
    this.message = message;
  }

  public Response getResponse() {
    return Response.status(status)
        .entity(message)
        .build();
  }
}
