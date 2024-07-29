package com.ecommerce.ecommerce.repositories;
import com.ecommerce.ecommerce.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
  Optional<Client> findByDocnumber(Integer docnumber);
}