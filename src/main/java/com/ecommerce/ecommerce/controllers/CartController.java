package com.ecommerce.ecommerce.controllers;
import com.ecommerce.ecommerce.entities.Cart;
import com.ecommerce.ecommerce.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/carts")
public class CartController {
  @Autowired
  private CartService cartService;

  @PutMapping("/{clientId}/{productId}/{amount}")
  public ResponseEntity<Cart> addToCart(@PathVariable Integer clientId, @PathVariable Integer productId, @PathVariable Integer amount) {
    try {
      cartService.addToCart(clientId, productId, amount);
      return ResponseEntity.ok().build();
    } catch (Exception error) {
      System.out.println(error.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @GetMapping
  public ResponseEntity<List<Cart>> getAllCart() {
    try {
      List<Cart> cart = cartService.readAllCart();
      if (!cart.isEmpty()) {
        return ResponseEntity.ok(cart);
      } else {
        return ResponseEntity.noContent().build();
      }
    } catch (Exception error) {
      System.out.println(error.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  // Método para eliminar un producto del carrito de un cliente
  @DeleteMapping("/{id}")
  public ResponseEntity<Map<String, Boolean>> deleteOneProductFromCart(@PathVariable Integer id) {
    try {
      Optional<Cart> optionalCart = cartService.findOneCart(id);
      if (optionalCart.isPresent()) {
        cartService.deleteOneCart(id);
        return ResponseEntity.ok().build();
      } else {
        return ResponseEntity.notFound().build();
      }
    } catch (Exception error) {
      System.out.println(error.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}