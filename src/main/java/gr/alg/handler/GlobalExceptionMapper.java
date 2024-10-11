package gr.alg.handler;

import gr.alg.exceptions.CustomException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Exception> {

  @Override
  public Response toResponse(Exception exception) {
    CustomException customException;

    if (exception instanceof CustomException) {
      customException = ((CustomException) exception);
    } else {
       customException = new CustomException(exception);
    }

    return customException.getResponse();
  }
}
