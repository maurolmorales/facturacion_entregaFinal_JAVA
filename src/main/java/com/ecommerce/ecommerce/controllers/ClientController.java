package com.ecommerce.ecommerce.controllers;
import com.ecommerce.ecommerce.entities.Client;
import com.ecommerce.ecommerce.services.ClientService;
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
@RequestMapping("api/v1/auth")
@Tag(name = "Ruta de clientes", description = "Una API para gestionar clientes")
public class ClientController {
  @Autowired private ClientService clientService;


  @PostMapping("/register")
  @Operation(summary = "Crea un nuevo cliente", description = "Agrega un nuevo cliente a la DB")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente.",
      content = @Content(mediaType = "application/json",
      examples = @ExampleObject(value = "{}"))),
    @ApiResponse(responseCode = "409", description = "Cliente ya registrado.",
      content = @Content(mediaType = "application/json",
      examples = @ExampleObject(value = "{ \"message\": \"Cliente ya registrado.\" }"))),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor",
      content = @Content(mediaType = "application/json",
      examples = @ExampleObject(value = "{ \"message\": \"Internal server error occurred.\" }"))),
  })
  public ResponseEntity<Object> createClient(@RequestBody Client client){
    try{
      // lógica para verificar si un cliente ya existe en a base de datos.
      Optional<Client> existingClient = clientService.findByDocumentNumber(client.getDocnumber());
      if(existingClient.isPresent()){
        // Si el cliente ya existe, devuelve una respuesta con el código de estado 409 (Conflict).
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("message", "Customer already registered."));
      }
      // Si el cliente no existe, lo crea y devuelve una respuesta con el código de estado 201 (Created).
      Client createdClient = clientService.saveClient(client);
      return ResponseEntity.status(HttpStatus.CREATED).body(createdClient);
    }catch(Exception error){
        if (error.getMessage().contains("empty")){ return ResponseEntity.status((HttpStatus.NOT_FOUND))
                .body(Map.of("message", error.getMessage())); }
      //Si ocurre una excepción, devuelve una respuesta con el código de estado 500 (Internal Server Error).
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(Map.of("message", "Internal Server Error"));
    }
  }



  @GetMapping
  @Operation(summary = "Consigue todos los clientes", description = "Recupera la lista de todos los clientes de la DB")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Devuelve correctamente todos los clientes",
      content = @Content(schema = @Schema(implementation = Client.class))),
    @ApiResponse(responseCode = "204", description = "No hay clientes registrados",
      content = @Content(mediaType = "application/json",
      examples = @ExampleObject(value = "{ \"message\": \"No hay clientes registrados\" }"))),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor",
      content = @Content(mediaType = "application/json",
      examples = @ExampleObject(value = "{ \"message\": \"Internal server error occurred.\" }"))),
  })
  public ResponseEntity<Object> getAllClient(){
    try{
      List<Client> clients = clientService.findAllClient();
      if(clients.isEmpty()){
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(Map.of("message", "There are no registered customers"));
      }
      else{ return ResponseEntity.ok(clients); }
    }catch (Exception error){
      System.out.println(error.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(Map.of("message", "Internal Server Error"));
    }
  }



  @GetMapping("/{id}")
  @Operation(summary = "Obtiene un cliente por el ID", description = "Recupera un cliente específico por su ID")
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
      Optional<Client> client = clientService.findOneClient(id);
      if(client.isEmpty()){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", "Customer not found with id: "));
      }
      else{ return ResponseEntity.ok(client.get()); }
    }catch (Exception error){
      System.out.println(error.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(Map.of("message", "Internal Server Error"));
    }
  }



  @PatchMapping("/me/{id}")
  @Operation(summary = "Actualiza un Cliente", description = "Actualizar los detalles de un cliente existente por su ID")
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
  public ResponseEntity<Object> updatePartial(@PathVariable Integer id, @RequestBody Map<String, Object> updates){
    try{
      Optional<Client> clientOptional = clientService.findOneClient(id);
      if (clientOptional.isEmpty()){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", "Customer not found with id: "));
      }
      Client updatedClient = clientService.updateClientPartial(id, updates);
      return ResponseEntity.ok(updatedClient);
    }catch (Exception error){
      System.out.println(error.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(Map.of("message", "Internal Server Error"));
    }
  }



  @DeleteMapping("/{id}")
  @Operation(summary = "Elimina un cliente", description = "Eliminar un cliente de la base de datos por su ID")
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
  public ResponseEntity<Object> deleteProduct(@PathVariable Integer id){
    try{
      Optional<Client> optionalClient = clientService.findOneClient(id);
      if(optionalClient.isEmpty()){
        // Si el cliente no se encuentra, devuelve una respuesta con el código de estado 404 (Not Found).
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", "Customer not found with id: "+id));
      }
      else{
        clientService.deleteClient(id);
        // Si el cliente se encuentra, procede a eliminarlo y devuelve una respuesta
        // con el código de estado 204 (No Content)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
      }
    }catch (Exception error){
      System.out.println(error.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(Map.of("message", "Internal Server Error"));
    }
  }

}
