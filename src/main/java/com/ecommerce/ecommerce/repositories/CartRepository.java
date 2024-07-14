package com.ecommerce.ecommerce.repositories;
import com.ecommerce.ecommerce.entities.Cart;
import com.ecommerce.ecommerce.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

  // Busca todos los carritos asociados a un cliente específico.
  // @param client El cliente cuyo carrito se desea obtener. Este parámetro es del tipo Client.
  // @return Una lista de objetos Cart que pertenecen al cliente especificado.
  //  La consulta JPQL selecciona todos los carritos (Cart) en los que el campo clientCart coincide
  //  con el cliente proporcionado.

  @Query("SELECT c FROM Cart c WHERE c.clientCart = :client")
  List<Cart> findByClientCart(@Param("client") Client client);
}
