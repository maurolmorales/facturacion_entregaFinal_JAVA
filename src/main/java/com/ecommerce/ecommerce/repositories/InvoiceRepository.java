package com.ecommerce.ecommerce.repositories;
import com.ecommerce.ecommerce.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
   List<Invoice> findLastByClientInvoice_ClientId(Integer client_id);
}