package gr.alg.controllers;

import static gr.alg.constants.ControllerConstants.API_V1;
import static gr.alg.constants.ControllerConstants.CREATE;
import static gr.alg.constants.ControllerConstants.ORDER;

import gr.alg.dto.request.NewOrderDto;
import gr.alg.service.OrderService;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import lombok.RequiredArgsConstructor;

@Path( API_V1 + ORDER)
@RequiredArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderController {

  private final OrderService orderService;

  @POST
  @Path(CREATE)
  public Response createNewOrder(@Valid NewOrderDto newOrderDto) {
    orderService.createOrder(newOrderDto);

    return Response
        .status(Status.CREATED)
        .entity("order placed successfully")
        .build();
  }
}
