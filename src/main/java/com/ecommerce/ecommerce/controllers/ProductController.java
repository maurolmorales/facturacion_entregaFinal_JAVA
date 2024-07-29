package com.ecommerce.ecommerce.controllers;
import com.ecommerce.ecommerce.entities.Client;
import com.ecommerce.ecommerce.entities.Product;
import com.ecommerce.ecommerce.services.ProductService;
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
import java.util.Optional;

@RestController
@RequestMapping("api/v1/products")
@Tag(name = "Ruta de Productos", description = "Una API para gestionar los Productos")
public class ProductController {
  @Autowired private ProductService productService;



  @PostMapping
  @Operation(summary = "Crea un nuevo producto", description = "Agrega un nuevo producto a la DB")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Producto creado exitosamente."),
    @ApiResponse(responseCode = "409", description = "Producto ya ingresado.",
      content = @Content(mediaType = "application/json",
      examples = @ExampleObject(value = "{ \"message\": \"Producto ya registrado.\" }"))),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor",
      content = @Content(mediaType = "application/json",
      examples = @ExampleObject(value = "{ \"message\": \"Internal server error occurred.\" }"))),
  })
  public ResponseEntity<Object> saveProduct(@RequestBody Product product){
    try {
      Product createdProduct = productService.saveProduct(product);
      return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }catch (RuntimeException error){
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
              .body(Map.of("message", "Producto no encontrado"));
    }catch (Exception error){
      System.out.println(error.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(Map.of("message", "Error Interno del Servidor"));
    }
  }



  @GetMapping
  @Operation(summary = "Consigue todos los productos", description = "Recupera la lista de todos los productos de la DB")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Devuelve correctamente todos los productos",
      content = @Content(schema = @Schema(implementation = Product.class))),
    @ApiResponse(responseCode = "204", description = "No hay productos disponibles",
      content = @Content(mediaType = "application/json",
      examples = @ExampleObject(value = "{ \"message\": \"No hay productos disponibles.\" }"))),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor",
      content = @Content(mediaType = "application/json",
      examples = @ExampleObject(value = "{ \"message\": \"Internal server error occurred.\" }"))),
  })
  public ResponseEntity<Object> getAllProducts(){
    try{
      List<Product> products = productService.readAllProduct();
      if(products.isEmpty()){
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(Map.of("message", "No hay productos disponibles"));
      }
      else{ return ResponseEntity.ok(products);  }
    }catch (Exception error){
      System.out.println(error.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(Map.of("message", "Error Interno del Servidor"));
    }
  }



  @GetMapping("/{id}")
  @Operation(summary = "Obtiene un producto por el ID", description = "Recupera un producto específico por su ID")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Cliente encontrado",
      content = @Content(schema = @Schema(implementation = Client.class))),
    @ApiResponse(responseCode = "204", description = "Cliente no encontrado",
      content = @Content(mediaType = "application/json",
      examples = @ExampleObject(value = "{ \"message\": \"Cliente no encontrado\" }"))),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor",
      content = @Content(mediaType = "application/json",
      examples = @ExampleObject(value = "{ \"message\": \"Error interno del servidor\" }"))),
  })
  public ResponseEntity<Object> getOneProduct(@PathVariable Integer id){
    try{
      Optional<Product> product = productService.readOneProduct(id);
      if(product.isEmpty()){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", "Producto no Enontrado.")) ;
      }
      else{ return ResponseEntity.ok(product.get()); }
    }catch (Exception error){
      System.out.println(error.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", "Error Interno del Servidor"));
    }
  }



  @PatchMapping("/{id}")
  @Operation(summary = "Actualiza un Producto", description = "Actualizar los detalles de un producto existente por su ID")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Client updated successfully",
      content = @Content(mediaType = "application/json",
      schema = @Schema(implementation = Client.class),
      examples = @ExampleObject(value = "{ \"clientId\": 1, \"name\": \"John\", \"lastname\": \"Doe\", \"docnumber\": 123456789 }"))),
    @ApiResponse(responseCode = "404", description = "Client not found",
      content = @Content(mediaType = "application/json",
      examples = @ExampleObject(value = "{ \"message\": \"Client not found with id: 1\" }"))),
    @ApiResponse(responseCode = "500", description = "Internal server error",
      content = @Content(mediaType = "application/json",
      examples = @ExampleObject(value = "{ \"message\": \"Internal server error occurred.\" }")))
  })
   public ResponseEntity<Object> updateProduct(@PathVariable Integer id, @RequestBody Map<String, Object> updates){
    try{
      Optional<Product> productOptional = productService.readOneProduct(id);
      if (productOptional.isEmpty()){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
              .body(Map.of("message", "Cliente no encontrado!!!"));
      }else{
        Product updatedProduct = productService.updatePartial(id, updates);
        return ResponseEntity.ok(updatedProduct);
      }
    }catch (Exception error){
      if (error.getMessage().contains("empty")){ return ResponseEntity.status((HttpStatus.NOT_FOUND))
              .body(Map.of("message", error.getMessage())); }
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(Map.of("message", "Error Interno del Servidor"));
    }
  }



  @DeleteMapping("/{id}")
  @Operation(summary = "Elimina un producto", description = "Eliminar un producto de la base de datos por su ID")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "204", description = "Cliente eliminado con éxito.",
      content = @Content(mediaType = "application/json",
      examples = @ExampleObject(value = "{}"))),
    @ApiResponse(responseCode = "404", description = "Cliente no encontrado.",
      content = @Content(mediaType = "application/json",
      examples = @ExampleObject(value = "{ \"message\": \"Cliente no encontrado con id: 1\" }"))),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor",
      content = @Content(mediaType = "application/json",
      examples = @ExampleObject(value = "{ \"message\": \"Error interno del servidor.\" }")))
  })
  public ResponseEntity<Map<String, String>> deleteProduct(@PathVariable Integer id){
    try{
      Optional<Product> optionalProduct = productService.readOneProduct(id);
      if(optionalProduct.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", "Customer not found with id: "+id));
      }
      else{
        productService.deleteOneProduct(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
      }
    }catch (Exception error){
      System.out.println(error.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(Map.of("message", "Internal Server Error"));
    }
  }

}