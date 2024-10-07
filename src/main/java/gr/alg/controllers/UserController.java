package gr.alg.controllers;

import gr.alg.entity.UserDto;
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

@Path("/api/v1/user")
@RequiredArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {

  private final UserService userService;

  @POST
  @Path("/register")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response registerUser(@Valid UserDto userDto) {
    userService.createUser(userDto);

    return Response
        .status(Status.CREATED)
        .entity("user created")
        .build();
  }

  @GET
  @Path("/find-user/{username}")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response getUser(@PathParam("username") String username) {
    var user = userService.findUser(username);

    return Response
        .status(Status.FOUND)
        .entity(user)
        .build();
  }
}
