package com.ecommerce.ecommerce.services;
import com.ecommerce.ecommerce.entities.Cart;
import com.ecommerce.ecommerce.entities.Client;
import com.ecommerce.ecommerce.entities.Product;
import com.ecommerce.ecommerce.repositories.CartRepository;
import com.ecommerce.ecommerce.repositories.ClientRepository;
import com.ecommerce.ecommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {
  @Autowired private CartRepository cartRepository;
  @Autowired private ProductRepository productRepository;
  @Autowired private ClientRepository clientRepository;

  public void addToCart(Integer clientId, Integer productId, Integer amount) throws Exception {
    // Busca el cliente por su ID en el repositorio de clientes. Idem con el producto:
    Optional<Client> clientOptional = clientRepository.findById(clientId);
    Optional<Product> productOptional = productRepository.findById(productId);

    // Verifica si el cliente y el producto existe:
    if (clientOptional.isEmpty()) { throw new Exception("Client not found with id: " + clientId); }
    if (productOptional.isEmpty()) { throw new Exception("Product not found with id: " + productId); }

    // Obtiene el cliente y el producto encontrados:
    Client client = clientOptional.get();
    Product product = productOptional.get();

    // Crea una nueva instancia de Cart:
    Cart cartNew = new Cart();
    cartNew.setClientCart(client);
    cartNew.setProductCart(product);
    cartNew.setAmount(amount);
    cartNew.setPrice(product.getPrice());
    cartRepository.save(cartNew);
  }

  public void removeFromCart(Integer cartId, Integer productId) throws Exception {
    Optional<Cart> cartOptional = cartRepository.findById(cartId);
    Optional<Product> productOptional = productRepository.findById(productId);
    if (cartOptional.isEmpty()) { throw new Exception("Client not found with id: " + cartId); }
    if (productOptional.isEmpty()) { throw new Exception("Product not found with id: " + productId); }
    Cart cart = cartOptional.get();
    Product product = productOptional.get();
    System.out.println(cart);
  }

  public List<Cart> readAllCart(){
    return cartRepository.findAll();
  }

  public Optional<Cart> findOneCart(Integer id){
    return cartRepository.findById(id);
  }

  public void deleteOneCart(Integer id){
    cartRepository.deleteById(id);
  }
}
