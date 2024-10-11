package gr.alg.exceptions;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomException extends Exception {
  private Status status;
  private String message;

  public CustomException(Exception exception) {
    this(exception, Status.BAD_REQUEST);
  }

  public CustomException(Exception exception, Status status) {
    this.status = status;
    this.message = exception.getMessage();
  }

  public Response getResponse() {
    return Response.status(status)
        .entity(message)
        .build();
  }
}
