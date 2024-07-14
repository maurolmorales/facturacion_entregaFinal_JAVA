package com.ecommerce.ecommerce.controllers;
import com.ecommerce.ecommerce.entities.Product;
import com.ecommerce.ecommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {
  @Autowired private ProductService productService;

  @PostMapping
  public ResponseEntity<Product> saveProduct(@RequestBody Product product){
    try{
      return new ResponseEntity<>(productService.saveProduct(product), HttpStatus.CREATED);
    }catch (Exception error){
      System.out.println(error.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @GetMapping
  public ResponseEntity<List<Product>> getAllProducts(){
    try{
      List<Product> products = productService.readAllProduct();
      if(!products.isEmpty()){ return ResponseEntity.ok(products); }
      else{ return ResponseEntity.noContent().build(); }
    }catch (Exception error){
      System.out.println(error.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<Object> getOneProduct(@PathVariable Integer id){
    try{
      Optional<Product> product = productService.readOneProduct(id);
      if(!product.isEmpty()){ return ResponseEntity.ok(product.get()); }
      else{ return ResponseEntity.notFound().build(); }
    }catch (Exception error){
      System.out.println(error.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @PatchMapping("/{id}")
  public ResponseEntity<Product> updateProduct(@PathVariable Integer id, @RequestBody Map<String, Object> updates){
    try{
      Product updatedProduct = productService.updatePartial(id, updates);
      return ResponseEntity.ok(updatedProduct);
    }catch (Exception error){
      System.out.println(error.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Map<String, Boolean>> deleteProduct(@PathVariable Integer id){
    try{
      Optional<Product> optionalProduct = productService.readOneProduct(id);
      if(optionalProduct.isPresent()){ productService.deleteOneProduct(id);
        return ResponseEntity.ok().build();}
      else{ return ResponseEntity.notFound().build(); }
    }catch (Exception error){
      System.out.println(error.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

}