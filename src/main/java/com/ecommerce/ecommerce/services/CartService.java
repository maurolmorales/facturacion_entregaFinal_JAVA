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



  public Cart addToCart(Integer clientId, Integer productId, Integer amount) throws Exception {
    // Busca el cliente por su Id y verifica si existe:
    Optional<Client> clientOptional = clientRepository.findById(clientId);
    if (clientOptional.isEmpty()) { throw new Exception("Client not found with id: " + clientId); }

    // Busca el producto por su Id y verifica si existe:
    Optional<Product> productOptional = productRepository.findById(productId);
    if (productOptional.isEmpty()) { throw new Exception("Product not found with id: " + productId); }

    // Obtiene el cliente y el producto encontrados:
    Client client = clientOptional.get();
    Product product = productOptional.get();

    // Verifica si el stock del producto es suficiente:
    if (product.getStock() < amount){
      throw new Exception("There is insufficient stock for the product id: " + productId);
    }

    // Crea una nueva instancia de Cart:
    Cart cartNew = new Cart();
    cartNew.setClientCart(client);
    cartNew.setProductCart(product);
    cartNew.setAmount(amount);
    cartNew.setPrice(product.getPrice());
    cartNew.setDelivered(false);
    cartRepository.save(cartNew);

    // Reduce el stock del producto.
    product.setStock(product.getStock() - amount);
    productRepository.save(product);

    return cartNew;
  }



  public List<Cart> findCartByClient(Integer clientId) throws Exception{
    List<Cart> clientOptional = cartRepository.findByClientCart_ClientIdAndDeliveredFalse(clientId);
    if(clientOptional.isEmpty()){ throw new Exception("Customer not found"); }
    return clientOptional;
  }



  public Object removeFromCart(Integer cartId) throws Exception {
    // Busca el Carrito por su Id y verifica si existe:
    Optional<Cart> cartOptional = cartRepository.findById(cartId);
    if (cartOptional.isEmpty()) { throw new Exception("Cart not found with id: " + cartId); }
    else{
      Cart cartFinded = cartOptional.get();
      if (cartFinded.getInvoiceCart() == null ){
        cartRepository.deleteById((cartId));
        return cartOptional.get();
      }else{
        throw new Exception("the cart was paid " + cartId);
      }
    }
  }

}