package gr.alg.controllers;

import static gr.alg.constants.ControllerConstants.API_V1;
import static gr.alg.constants.ControllerConstants.FIND_USER;
import static gr.alg.constants.ControllerConstants.REGISTER;
import static gr.alg.constants.ControllerConstants.USER;
import static gr.alg.constants.ControllerConstants.USERNAME;

import gr.alg.dto.request.UserRegistrationDto;
import gr.alg.service.UserService;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import lombok.RequiredArgsConstructor;

@Path( API_V1 + USER)
@RequiredArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {
  private final UserService userService;

  @POST
  @Path(REGISTER)
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response registerUser(@Valid UserRegistrationDto userDto) {
    userService.createUser(userDto);

    return Response
        .status(Status.CREATED)
        .entity("user created")
        .build();
  }

  @GET
  @Path( FIND_USER + "/{username}")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response getUser(@PathParam(USERNAME) String username) {
    var user = userService.findUser(username);

    return Response
        .status(Status.FOUND)
        .entity(user)
        .build();
  }
}
