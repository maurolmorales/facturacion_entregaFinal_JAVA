package com.ecommerce.ecommerce.repositories;
import com.ecommerce.ecommerce.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
  List<Cart> findByClientCart_ClientIdAndDeliveredFalse(Integer clientId);
}
