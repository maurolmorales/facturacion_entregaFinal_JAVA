package com.ecommerce.ecommerce.controllers;
import com.ecommerce.ecommerce.entities.Cart;
import com.ecommerce.ecommerce.services.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/carts")
@Tag(name = "Ruta de los Carritos", description = "Una API para gestionar los Carritos")
public class CartController {
  @Autowired
  private CartService cartService;




  @PutMapping("/{clientId}/{productId}/{amount}")
  @Operation(summary = "Añade un producto al carrito", description = "Agrega un nuevo producto al carrito del cliente especificado.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Producto añadido al carrito exitosamente",
      content = @Content(mediaType = "application/json",
      schema = @Schema(implementation = Cart.class),
      examples = @ExampleObject(value = "{ \"message\": \"Producto ingresado al carrito.\" }"))),
    @ApiResponse(responseCode = "404", description = "Cliente o producto no encontrado",
      content = @Content(mediaType = "application/json",
      examples = @ExampleObject(value = "{ \"message\": \"Cliente o producto no encontrado.\" }"))),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor",
      content = @Content(mediaType = "application/json",
      examples = @ExampleObject(value = "{ \"message\": \"Error interno del servidor.\" }"))
    )
  })
  public ResponseEntity<Object> addToCart(@PathVariable Integer clientId, @PathVariable Integer productId,
                                          @PathVariable Integer amount) {
    try {
      Cart cart = cartService.addToCart(clientId, productId, amount);
      return ResponseEntity.ok(cart);
    } catch (Exception error) {
      String errorMessage = error.getMessage();
      if (errorMessage.contains("Client not found")){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", errorMessage));
      }else if(errorMessage.contains("Product not found")){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", errorMessage));
      }else{
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", "Internal Server Error"));
      }
    }
  }



  @GetMapping("/{clientId}")
  @Operation(summary = "Consigue los carritos de un cliente específico",
    description = "Recupera la lista de todos los carritos no entregados (delivered = false) de la base de datos para un cliente específico.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Carritos recuperados exitosamente",
      content = @Content(mediaType = "application/json",
      schema = @Schema(implementation = Cart.class),
      examples = @ExampleObject(value = "{ \"message\": \"Carritos recuperados exitosamente.\" }"))),
    @ApiResponse(responseCode = "204", description = "No hay carritos disponibles para el cliente",
      content = @Content(mediaType = "application/json",
      examples = @ExampleObject(value = "{ \"message\": \"No hay carritos disponibles para el cliente.\" }"))),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor",
      content = @Content(mediaType = "application/json",
      examples = @ExampleObject(value = "{ \"message\": \"Error interno del servidor.\" }")))
  })
  public ResponseEntity<Object> getCartsByClientId(@PathVariable Integer clientId) {
    try {
      List<Cart> cartClientId = cartService.findCartByClient(clientId);
      if (cartClientId.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
      } else {
        return ResponseEntity.ok(cartClientId);
      }
    } catch (Exception error) {
      System.out.println(error.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(Map.of("message", "Internal Server Error"));
    }
  }




  @DeleteMapping("/{id}")
  @Operation(summary = "Elimina un carrito", description = "Elimina un carrito de la base de datos por su ID.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Carrito eliminado exitosamente",
      content = @Content(mediaType = "application/json",
      examples = @ExampleObject(value = "{ \"message\": \"Carrito eliminado exitosamente.\" }"))),
    @ApiResponse(responseCode = "404", description = "Carrito no encontrado",
      content = @Content(mediaType = "application/json",
      examples = @ExampleObject(value = "{ \"message\": \"Carrito no encontrado.\" }"))),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor",
      content = @Content(mediaType = "application/json",
      examples = @ExampleObject(value = "{ \"message\": \"Error interno del servidor.\" }")))
  })
  public ResponseEntity<Object> deleteOneProductFromCart(@PathVariable Integer id) {
    try {
      cartService.removeFromCart(id);
      return ResponseEntity.ok(Map.of("message", "Cart deleted successfully."));
    } catch (Exception error) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(Map.of("message", error.getMessage()));
    }
  }
}