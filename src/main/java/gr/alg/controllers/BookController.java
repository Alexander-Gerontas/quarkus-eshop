package gr.alg.controllers;

import static gr.alg.constants.ControllerConstants.API_V1;
import static gr.alg.constants.ControllerConstants.BOOK;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path(API_V1 + BOOK)
public class BookController {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from Quarkus REST";
    }
}
