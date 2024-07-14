package com.ecommerce.ecommerce.controllers;
import com.ecommerce.ecommerce.entities.Client;
import com.ecommerce.ecommerce.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/auth")
public class ClientController {
  @Autowired private ClientService clientService;

  @PostMapping("/register")
  public ResponseEntity<Client> createClient(@RequestBody Client client){
    try{
      return new ResponseEntity<>(clientService.saveClient(client), HttpStatus.CREATED);
    }catch(Exception error){
      System.out.println(error.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @GetMapping
  public ResponseEntity<List<Client>> getAllClient(){
    try{
      List<Client> clients = clientService.findAllClient();
      if(!clients.isEmpty()){ return ResponseEntity.ok(clients); }
      else{ return ResponseEntity.noContent().build(); }
    }catch (Exception error){
      System.out.println(error.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<Object> getOneProduct(@PathVariable Integer id){
    try{
      Optional<Client> client = clientService.findOneClient(id);
      if(!client.isEmpty()){ return ResponseEntity.ok(client.get()); }
      else{ return ResponseEntity.notFound().build(); }
    }catch (Exception error){
      System.out.println(error.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @PatchMapping("/me/{id}")
  public ResponseEntity<Client> updatePartial(@PathVariable Integer id, @RequestBody Map<String, Object> updates){
    try{
      Client updatedClient = clientService.updateClientPartial(id, updates);
      return ResponseEntity.ok(updatedClient);
    }catch (Exception error){
      System.out.println(error.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Map<String, Boolean>> deleteProduct(@PathVariable Integer id){
    try{
      Optional<Client> optionalClient = clientService.findOneClient(id);
      if(optionalClient.isEmpty()){ clientService.deleteClient(id);
        return ResponseEntity.ok().build();}
      else{ return ResponseEntity.notFound().build(); }
    }catch (Exception error){
      System.out.println(error.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

}
