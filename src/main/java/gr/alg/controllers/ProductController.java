package gr.alg.controllers;

import static gr.alg.constants.ControllerConstants.API_V1;
import static gr.alg.constants.ControllerConstants.CREATE;
import static gr.alg.constants.ControllerConstants.FIND_PRODUCT;
import static gr.alg.constants.ControllerConstants.PRODUCT;
import static gr.alg.constants.ControllerConstants.PRODUCT_ID;

import gr.alg.dto.request.CreateProductDto;
import gr.alg.service.ProductService;
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

@Path( API_V1 + PRODUCT)
@RequiredArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductController {
  private final ProductService productService;

  @POST
  @Path(CREATE)
  public Response createNewProduct(@Valid CreateProductDto productDto) {
    productService.createProduct(productDto);

    return Response
        .status(Status.CREATED)
        .entity("product created successfully")
        .build();
  }

  @GET
  @Path( FIND_PRODUCT + "/{product-id}")
  public Response getProduct(@PathParam(PRODUCT_ID) Long productId) {
    var productResponseDto = productService.findProductById(productId);

    return Response
        .status(Status.FOUND)
        .entity(productResponseDto)
        .build();
  }
}
